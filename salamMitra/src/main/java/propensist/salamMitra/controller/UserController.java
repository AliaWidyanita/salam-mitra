// package propensist.salamMitra.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.RequestParam;

// import propensist.salamMitra.model.user.UserModel;
// import propensist.salamMitra.service.UserService;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;



// @Controller
// public class UserController {

//     private final UserService userService;

//     public UserController(UserService userService){
//         this.userService = userService;
//     }

//     @GetMapping("/register")
//     public String getRegisterPage(Model model) {
//         model.addAttribute("registerRequest", new UserModel());
//         return "register-page";
//     }

//     @GetMapping("/login")
//     public String getLoginPage(Model model) {
//         model.addAttribute("loginRequest", new UserModel());
//         return "login-page";
//     }

//     @PostMapping("/register")
//     public String register(@ModelAttribute UserModel usersModel) {
//         System.out.println("register request: " + usersModel);
//         UserModel registeredUser = userService.registerUser(usersModel.getUsername(), usersModel.getPassword(), usersModel.getEmail());
        
//         return registeredUser == null ? "error-page" : "redirect:/login";
//     }

//     @PostMapping("/login")
//     public String login(@ModelAttribute UserModel usersModel, Model model) {
//         System.out.println("login request: " + usersModel);
//         UserModel authenticated = userService.authenticate(usersModel.getUsername(), usersModel.getPassword());
//         if (authenticated != null){
//             model.addAttribute("userLogin", authenticated.getUsername());
//             return "personal-page";
//         } else {
//             return "error-page";
//         }
//     }
    
    
    
// }
