package application.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.dao.FoglalasDAO;
import application.dao.SzallasDAO;
import application.dao.UserDAO;
import application.dao.VelemenyekDAO;
import application.model.Foglalas;
import application.model.Review;
import application.model.Szallas;
import application.model.User;

@Controller
public class UserController {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private SzallasDAO szallasDAO;

  @Autowired
  private FoglalasDAO foglalasDAO;

  @Autowired
  private VelemenyekDAO reviewDAO;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String homePage(HttpSession session, Model model) {
    List<Review> reviews = reviewDAO.listVelemenyek();
    model.addAttribute("ertekelesek", reviews.subList(0, Math.min(reviews.size(), 6)));

    return "index";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/contact")
  public String contact() {
    return "contact";
  }

  @GetMapping("/profil")
  public String getProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = authentication.getName();
    User user = userDAO.getUserByEmail(userEmail);
    model.addAttribute("session", user);

    // Lefoglalt szallasok (ROLE_felhasznalo)
    if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_felhasznalo"))) {
      model.addAttribute("foglalasok", Optional.ofNullable(foglalasDAO.getFoglalasokByFelhasznaloId(user.getId()))
          .orElse(Collections.emptyList()));
    }

    // Meghirdetett szallasok (ROLE_vendeglato)
    if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_vendeglato"))) {
      model.addAttribute("szallasok", Optional.ofNullable(szallasDAO.getSzallasokByFelhasznaloId(user.getId()))
          .orElse(Collections.emptyList()));
    }

    return "profile";
  }

  // Profiladatok módosítása
  @PostMapping("/updateUserDetails/{id}")
  public String update(@PathVariable("id") String userEmail,
      @RequestParam("felhasznalonev") String felhasznalonev,
      @RequestParam("email") String email,
      RedirectAttributes redirectAttributes,
      HttpSession session) {

    User user = userDAO.getUserByEmail(userEmail);

    // Visszajelzes - hiba
    if (user == null) {
      redirectAttributes.addFlashAttribute("error", "Hiba történt");
      return "redirect:/profil";
    }

    // Adatok megvaltoztatasa
    user.setNev(felhasznalonev);
    user.setEmail(email);
    session.setAttribute("felhasznalo", user);
    userDAO.updateUser(user);
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    

    // Visszajelzes - siker
    redirectAttributes.addFlashAttribute("success", "Sikeresen megváltoztatva");
    return "redirect:/profil";
  }

  // session-be belerakja az összes szállást, foglalást és usert
  @GetMapping("/getAllDataAdmin")
  public String getAllDataAdmin(Model model) {
    List<Szallas> szallasList = szallasDAO.listSzallasok();
    model.addAttribute("szallasok", szallasList);

    List<Foglalas> foglalasok = foglalasDAO.listFoglalasok();
    model.addAttribute("foglalasok", foglalasok);

    List<User> users = userDAO.listUsers();
    model.addAttribute("felhasznalok", users);

    return "index";
  }

  // regisztrálás http request handler
  @PostMapping(value = "/registeruser")
  public String registerUser(@RequestParam("name") String name, @RequestParam("email") String email,
      @RequestParam("password") String password, @RequestParam("confirmPassword") String password2) {
    if (password.equals(password2)) {
      User user = new User(name, email, password);
      userDAO.insertUser(user);
      return "redirect:/";
    } else
      return "redirect:/register?error=password_mismatch";
  }

  // bejelentkezés http request handler + session
  @PostMapping(value = "/loginuser")
  public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
      HttpSession session) {

    User user = userDAO.getUserByEmail(email);

    if (user != null && passwordEncoder.matches(password, user.getPassword())) {

      List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
      Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      session.setAttribute("felhasznalo", user);

      List<Szallas> szallasok = szallasDAO.getSzallasokByFelhasznaloId(user.getId());

      session.setAttribute("szallasok", szallasok);

      List<Foglalas> foglalasok = foglalasDAO.getFoglalasokByFelhasznaloId(user.getId());

      session.setAttribute("foglalasok", foglalasok);

      return "redirect:/";
    } else {

      return "redirect:/login?error=invalid_credentials";
    }
  }

  // Kijelentkezés
  @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
  public String logoutUser(HttpSession session) {
    session.invalidate();
    SecurityContextHolder.clearContext();
    return "redirect:/";
  }

}