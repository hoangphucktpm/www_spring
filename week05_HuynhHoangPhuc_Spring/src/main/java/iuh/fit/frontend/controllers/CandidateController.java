package iuh.fit.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.fit.backend.models.Address;
import iuh.fit.backend.models.Candidate;
import iuh.fit.backend.repositories.AddressRepository;
import iuh.fit.backend.repositories.CandidateRepository;
import iuh.fit.backend.services.CandidateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateServices candidateServices;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/list_no_paging";
    }

    @GetMapping("")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        /*Page<Candidate> candidatePage= candidateServices.findPaginated(
                PageRequest.of(currentPage - 1, pageSize)
        );*/
        Page<Candidate> candidatePage = candidateServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/list";
    }

    @GetMapping("/show-add-form")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("candidates/add");
        return modelAndView;
    }

    @PostMapping("/candidates/add")
    public String addCandidate(
            @ModelAttribute("candidate") Candidate candidate,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("countries", CountryCode.values());
            return "candidates/add";
        }

        addressRepository.save(candidate.getAddress());
        candidateRepository.save(candidate);

        return "redirect:/candidates";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Candidate> opt = candidateRepository.findById(id);
        if (opt.isPresent()) {
            Candidate candidate = opt.get();
            modelAndView.addObject("candidate", candidate);
            modelAndView.addObject("countries", CountryCode.values());

            modelAndView.setViewName("candidates/update");
        } else {
            modelAndView.setViewName("redirect:/candidates?error=candidateNotFound");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute("candidate") Candidate candidate,
            BindingResult result,
            Model model) {

        if (candidate.getAddress() == null || candidate.getAddress().getCountry() == null) {
            model.addAttribute("error", "Country is required.");
            model.addAttribute("candidate", candidate);
            model.addAttribute("countries", CountryCode.values());
            return "candidates/update";
        }

        Address address = candidate.getAddress();
        if (address.getId() == null) {
            addressRepository.save(address);
        } else {
            addressRepository.save(address);
        }
        candidate.setAddress(address);
        candidateRepository.save(candidate);

        return "redirect:/candidates?success=updateSuccess";
    }


    @GetMapping("/candidates/suggest-skill-to-learn/{id}")
    public String suggestSkillToLearn(@PathVariable Long id, Model model) {
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidate", candidate);
        model.addAttribute("suggestedSkills", candidateService.suggestSkillsToLearn(candidate));
        return "candidates/suggest-skill-to-learn";
    }

}