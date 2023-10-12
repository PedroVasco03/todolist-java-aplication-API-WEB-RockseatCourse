package ao.co.vascopedro.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificadores
 * public
 * private
 * protected
 */

 @RestController
 @RequestMapping("/users")
public class UserController {
    
    /**
     * String (texto)
     * Integer / int (Números)
     * Double (double) Números 0.0000
     * Float (float) Números 0.000
     * Char (A C )
     * Date (data)
     * void
     */

     @Autowired
     private IUserRepository userRepository;
     /**Body */

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        
        if( user != null ){
            System.out.println("Usuário ja existe");
            //Mensagem de erro 
            //Status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
