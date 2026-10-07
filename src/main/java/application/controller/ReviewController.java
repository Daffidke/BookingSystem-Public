package application.controller;

import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import application.dao.*;
import application.model.*;

@Controller
public class ReviewController {

    @Autowired
    private VelemenyekDAO ReviewDAO;

    @PostMapping(value = "/addreview/{id}")
    public String addReview(@RequestParam("id") int szallas_id, @RequestParam("ertekeles") int ertekeles,
            @RequestParam("uzenet") String uzenet, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");

        int felhasznalo_id = loggedInUser.getId();

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);

        Review review = new Review();

        review.setSzallasId(szallas_id);
        review.setFelhasznaloId(felhasznalo_id);
        review.setErtekeles(ertekeles);
        review.setUzenet(uzenet);

        ReviewDAO.insertVelemeny(review, currentTimestamp);
        return "addreview";
    }

    @PostMapping(value = "/deletereview/{id}")
    public String addReview(@RequestParam("id") int id) {

        ReviewDAO.deleteVelemeny(id);
        return "deletereview";
    }

    @PostMapping(value = "/editreview/{id}")
    public String editReview(@RequestParam("id") int id, @RequestParam("szallas_id") int szallas_id,
            @RequestParam("felhasznalo_id") int felhasznalo_id, @RequestParam("ertekeles") int ertekeles,
            @RequestParam("uzenet") String uzenet) {

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);

        ReviewDAO.updateVelemeny(id, szallas_id, felhasznalo_id, currentTimestamp, ertekeles, uzenet);
        return "editreview";
    }

    @GetMapping(value = "/listreviews")
    public String listReviews(Model model) {

        List<Review> reviews = ReviewDAO.listVelemenyek();
        model.addAttribute(reviews);

        return "listreview";
    }

    @PostMapping(value = "/listreviewbyszallas")
    public String listReviewBySzallas(@RequestParam("szallas_id") int szallas_id, Model model) {

        List<Review> reviews = ReviewDAO.getVelemenyekBySzallasId(szallas_id);
        model.addAttribute(reviews);

        return "listreviewbyszallas";
    }

    @PostMapping(value = "/listreviewbyfelhasznalo")
    public String listReviewByFelhasznalo(@RequestParam("felhasznalo_id") int felhasznalo_id, Model model) {

        List<Review> reviews = ReviewDAO.getVelemenyekByFelhasznaloId(felhasznalo_id);
        model.addAttribute(reviews);

        return "listreviewbyfelhasznalo";
    }

}
