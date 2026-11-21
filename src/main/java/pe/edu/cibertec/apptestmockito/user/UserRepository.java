package pe.edu.cibertec.apptestmockito.user;

interface UserRepository {
    boolean existsByEmail(String email);
    User save(User user);
}

