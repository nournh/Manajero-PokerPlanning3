package tn.esprit.pockerplanning.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pockerplanning.entities.Company;
import tn.esprit.pockerplanning.services.ICompanyServices;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/company")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")

public class CompanyController {
    private final ICompanyServices iCompanyServices;

    @PostMapping("{idUser}")
    public void addCompany(@RequestBody Company company,@PathVariable String idUser){
        iCompanyServices.addCompany(company,idUser);
    }
    @PutMapping
    public void updateCompany(@RequestBody Company company){
        iCompanyServices.updateCompany(company);
    }
    @GetMapping
    public List<Company> getAllCompany(){
       return  iCompanyServices.getAllCompany();
    }

    @PutMapping("/affecterCompanyADeveloper/{idCompany}/{idUser}")
    public Company affecterCompanyADeveloper(@PathVariable String idCompany , @PathVariable String idUser){
        return iCompanyServices.affecterCompanyADeveloper(idCompany,idUser);
    }

}
