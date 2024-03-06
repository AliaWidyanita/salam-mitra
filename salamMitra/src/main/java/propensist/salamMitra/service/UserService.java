// package propensist.salamMitra.service;

// import org.springframework.stereotype.Service;

// import propensist.salamMitra.model.user.UserModel;
// import propensist.salamMitra.repository.MitraDb;

// @Service
// public class UserService {
//     private final MitraDb userDb;

//     public UserService(MitraDb userDb) {
//         this.userDb = userDb;
//     }

//     public UserModel registerUser(String username, String password, String email) {
//         if (username == null || password == null) {
//             return null;
//         } else {
//             UserModel usersModel = new UserModel();
//             usersModel.setUsername(username);
//             usersModel.setPassword(password);
//             usersModel.setEmail(email);
//             usersModel.setDeleted(false);
//             usersModel.setRole("Mitra");
//             return userDb.save(usersModel);
//         }
//     }

//     public UserModel authenticate(String username, String password) {
//         return userDb.findByUsernameAndPassword(username, password).orElse(null);
//     }
    
// }
