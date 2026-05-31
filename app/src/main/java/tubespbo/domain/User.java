package tubespbo.domain;

public class User {
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        // User harus punya role valid karena role dipakai untuk menentukan dashboard.
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username wajib diisi.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password wajib diisi.");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role wajib diisi.");
        }

        this.username = username.trim();
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
