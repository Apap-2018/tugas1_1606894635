package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping("/")
	private String home() {
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

}
