package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("jabatan", jabatanService.getListJabatan());
		return "home";
	}
	
	//menampilkan pegawai
	@RequestMapping(value = "/pegawai")
	private String searchNIP(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		double gajiPokok = 0;
		for (JabatanModel jabatan: pegawai.getJabatanList()) {
			if (jabatan.getGajiPokok() > gajiPokok) {
				gajiPokok = jabatan.getGajiPokok();
			}
		}
		
		System.out.println(gajiPokok);
		double gaji = gajiPokok + (pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() *0.01 * gajiPokok);
		System.out.println(gaji);
		System.out.println(pegawai.getInstansi().getProvinsi().getPresentaseTunjangan());
		model.addAttribute("gaji", gaji);
		return "searchNip";
	}
	
	//membuat pegawai
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pegawai", new PegawaiModel());
		model.addAttribute("provinsi", provinsiService.getListProv());
		model.addAttribute("jabatan", jabatanService.getListJabatan());
		return "pegawaiTambah";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawai(@ModelAttribute PegawaiModel pegawai, Model model, @RequestParam(value = "jabatan") String jabatan) {
		System.out.println(jabatan);
		model.addAttribute("title", "Add Pilot");
		return "add";
	}

	@RequestMapping(value = "/pegawai/tambah/instansi", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value = "provinsiId", required = true) long provinsiId) {
	    ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(provinsiId);
	    return provinsi.getInstansiList(); 
	}

}
