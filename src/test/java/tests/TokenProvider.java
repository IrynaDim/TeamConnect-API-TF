package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.enums.Role;
import constants.Routes;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static util.EnvConfig.*;

@Slf4j
public final class TokenProvider {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final long EXP_GRACE_SECONDS = 10;

    private final Map<Role, Entry> cache = new ConcurrentHashMap<>();

    public String bearer(Role role) {
        return "Bearer " + token(role);
    }

    public String invalidBearer() {
        return "Bearer invalid_token";
    }

    public String token(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role must not be null");
        }
        Entry e = cache.compute(role, (r, cur) -> {
            if (cur == null || isExpired(cur)) {
                log.info("Fetching new token for role {}", r);
                return fetchNewToken(r);
            }
            return cur;
        });
        return e.token();
    }

    private boolean isExpired(Entry e) {
        boolean expired = Instant.now().isAfter(e.expiresAt().minusSeconds(EXP_GRACE_SECONDS));
        if (expired) {
            log.info("Token expired for one of the roles, refreshing.");
        }
        return expired;
    }

    private Entry fetchNewToken(Role role) {
        Response resp = RestAssured.given()
                .baseUri(authBaseUrl())
                .basePath(Routes.Auth.LOGIN.getPath())
                .contentType(ContentType.JSON)
                .body(credsOf(role))
                .post()
                .then()
                .extract()
                .response();

        if (resp.statusCode() != 200) {
            log.error("Login failed for role {} (HTTP {}): {}", role, resp.statusCode(), resp.asString());
            throw new IllegalStateException(String.format(
                    "Login failed for role %s (HTTP %d): %s",
                    role, resp.statusCode(), resp.asString()));
        }

        String token = resp.path("token");
        if (token == null || token.isBlank()) {
            log.error("Auth service returned empty token for role {}. Body: {}", role, resp.asString());
            throw new IllegalStateException("Auth service returned empty token");
        }

        Instant exp = extractExpFromJwt(token);
        log.debug("Token for {} expires at {}", role, exp);
        return new Entry(token, exp);
    }

    private Instant extractExpFromJwt(String jwt) {
        String[] parts = jwt.split("\\.");
        if (parts.length < 2) {
            throw new IllegalStateException("Token is not a JWT (no payload part)");
        }
        try {
            byte[] payload = Base64.getUrlDecoder().decode(parts[1]);
            JsonNode node = MAPPER.readTree(payload);
            if (!node.has("exp")) {
                throw new IllegalStateException("JWT has no 'exp' claim");
            }
            return Instant.ofEpochSecond(node.get("exp").asLong());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse JWT payload", e);
        }
    }

    private Creds credsOf(Role role) {
        return switch (role) {
            case EMPLOYEE -> new Creds(empEmail(), empPass());
            case PM -> new Creds(pmEmail(), pmPass());
            case HR -> new Creds(hrEmail(), hrPass());
            case ADMIN -> new Creds(admEmail(), admPass());
        };
    }

    private record Creds(String email, String password) {
    }

    private record Entry(String token, Instant expiresAt) {
    }
}
