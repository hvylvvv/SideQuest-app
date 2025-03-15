@Data
@AllArgsConstructor
@NoArgsConstructor

Public class User{
    @id
    private int userID;
    private String firstname;
    private String lastname;
    private String password_hash;
    private String nationality;
    private String email;
    private String phone_number;

}