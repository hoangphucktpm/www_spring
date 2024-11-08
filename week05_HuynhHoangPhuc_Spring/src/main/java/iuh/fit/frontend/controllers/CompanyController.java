package iuh.fit.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import iuh.fit.backend.models.Address;
import iuh.fit.backend.models.Candidate;
import iuh.fit.backend.models.Company;
import iuh.fit.backend.repositories.AddressRepository;
import iuh.fit.backend.repositories.CompanyRepository;
import iuh.fit.backend.services.CompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyServices companyServices;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/list_company")
    public String showCompanyList(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "companies/list_no_paging_company";
    }

    @GetMapping("")
    public String showCompanyListPaging(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        /*Page<Candidate> candidatePage= candidateServices.findPaginated(
                PageRequest.of(currentPage - 1, pageSize)
        );*/
        Page<Company> companyPage = companyServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("companyPage", companyPage);

        int totalPages = companyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "companies/list_company";
    }

    @GetMapping("/show-add-form")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = new Company();
        company.setAddress(new Address());
        modelAndView.addObject("company", company);
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("companies/add_company");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addCompany(@ModelAttribute("company") Company company,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("countries", CountryCode.values());
            return "companies/add_company";
        }

        addressRepository.save(company.getAddress());
        companyRepository.save(company);

        return "redirect:/companies";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView edit(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Company> opt = companyRepository.findById(id);
        if (opt.isPresent()) {
            Company company = opt.get();
            modelAndView.addObject("company", company);
            modelAndView.addObject("countries", CountryCode.values());
            modelAndView.setViewName("companies/update");
        } else {
            modelAndView.setViewName("redirect:/companies?error=companyNotFound");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("company") Company company,
                         BindingResult result, Model model) {

        if (company.getAddress() == null || company.getAddress().getCountry() == null) {
            model.addAttribute("error", "Country is required.");
            model.addAttribute("company", company);
            model.addAttribute("countries", CountryCode.values());
            return "companies/update";
        }

        Address address = company.getAddress();
        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);

        return "redirect:/companies?success=updateSuccess";
    }

    @GetMapping("/view_company/{id}")
    public ModelAndView viewCompany(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            modelAndView.addObject("company", company.get());
            modelAndView.setViewName("companies/view_detail_company");
        } else {
            modelAndView.setViewName("redirect:/jobs?error=companyNotFound");
        }
        return modelAndView;
    }


}
