package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;

	// melihat jabatan
	@RequestMapping(value = "/jabatan/view" , method = RequestMethod.GET)
	private String addJabatan(Model model, @RequestParam(value = "idJabatan") long idJabatan) {
		model.addAttribute("jabatan", jabatanService.findJabatanById(idJabatan));
		return "jabatanView";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "jabatanAdd";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String update(@RequestParam(value = "idJabatan") long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);		
		model.addAttribute("jabatan", jabatan);
		return "jabatanUpdate";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute JabatanModel jabatan, @RequestParam(value = "idJabatan") long idJabatan, Model model) {
		jabatan.setId(idJabatan);
		jabatanService.updateJabatan(jabatan);
		return "update";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(@RequestParam(value = "idJabatan") long idJabatan, Model model){
		jabatanService.deleteJabatan(idJabatan);
		return "delete";
		
	}

}
