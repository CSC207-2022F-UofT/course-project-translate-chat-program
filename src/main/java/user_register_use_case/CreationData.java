package user_register_use_case;

public class CreationData {
    private final String username;
    private final String password;
    private final String email;
    private final String default_lang;

    public CreationData(String username, String password, String email, String default_lang) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.default_lang = default_lang;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDefault_lang() {
        return default_lang;
    }

}
