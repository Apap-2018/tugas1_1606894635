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
import com.apap.tugas1.service.InstansiService;
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

	@Autowired
	private InstansiService instansiService;

	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("listOfJabatan", jabatanService.getListJabatan());
		model.addAttribute("listOfInstansi", instansiService.getInstansiList());
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
		return "pegawaiLihat";
	}

	//membuat pegawai	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listOfProvinsi", provinsiService.getListProv());
		model.addAttribute("listOfJabatan", jabatanService.getListJabatan());
		return "pegawaiTambah";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = "";
		nip += pegawai.getInstansi().getId();

		String[] tlSplit= pegawai.getTanggalLahir().toString().split("-");
		nip += tlSplit[2] + tlSplit[1] + tlSplit[0].substring(2, 4);

		System.out.println(nip);
		nip += pegawai.getTahunMasuk();

		int count = 1;
		for(PegawaiModel i: pegawai.getInstansi().getPegawaiInstansi()) {
			if (i.getTanggalLahir().equals(pegawai.getTanggalLahir()) && i.getTahunMasuk().equals(pegawai.getTahunMasuk())){
				count += 1;
				if (count >= 10) {
					count = count % 10;
				}
			}
		}
		nip += "0" + count;
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add";
	}

	//membuat pegawai	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubah(Model model, @RequestParam(value = "nip", required = true) String nip) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		System.out.println("ubah nama : " + pegawai.getNama());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listOfProvinsi", provinsiService.getListProv());
		model.addAttribute("listOfJabatan", jabatanService.getListJabatan());
		return "pegawaiUpdate";
	}

	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PegawaiModel pegawai, @RequestParam(value = "id", required = true) long idPegawai, Model model) {
		String nip = "";
		nip += pegawai.getInstansi().getId();

		String[] tlSplit= pegawai.getTanggalLahir().toString().split("-");
		nip += tlSplit[2] + tlSplit[1] + tlSplit[0].substring(2, 4);

		System.out.println(nip);
		nip += pegawai.getTahunMasuk();

		int count = 1;
		System.out.println("ini instansi");
		System.out.println("size :" + pegawai.getInstansi().getPegawaiInstansi().size());
		for(PegawaiModel i: pegawai.getInstansi().getPegawaiInstansi()) {
			if (i.getTanggalLahir().equals(pegawai.getTanggalLahir()) && i.getTahunMasuk().equals(pegawai.getTahunMasuk())){
				count += 1;
				if (count >= 10) {
					count = count % 10;
				}
			}
		}
		nip += "0" + count;
		System.out.println(nip);
		pegawai.getNama();
		pegawai.setNip(nip);
		System.out.println("keren" + pegawai.getNip());

		pegawaiService.updatePegawai(pegawai, idPegawai);

		System.out.println("update udah ke sini");
		model.addAttribute("nip", nip);
		return "update";
	}

	@RequestMapping(value = "/pegawai/instansi-get", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "instansiId", required = true) long instansiId) {
		System.out.println(instansiId);
		InstansiModel instansi = instansiService.getInstansiById(instansiId);
		System.out.println(instansi);
		return instansi;
	}

	@RequestMapping(value = "/pegawai/provinsi-get", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value = "provinsiId", required = true) long provinsiId, Model model) {

		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(provinsiId);
		return provinsi.getInstansiList(); 
	}

	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String tertuaTermuda(@RequestParam(value = "idInstansi") long id, Model model) {
		List<PegawaiModel> listOfPegawai =  instansiService.getInstansiById(id).getPegawaiInstansi();
		PegawaiModel muda = listOfPegawai.get(0);

		for(int i = 0; i < listOfPegawai.size(); i++) {
			if(i+1 != listOfPegawai.size()) {
				if(	muda.getTanggalLahir().after(listOfPegawai.get(i+1).getTanggalLahir())) {
					muda = listOfPegawai.get(i+1);
				}
			}
		}
		PegawaiModel tua = listOfPegawai.get(0);

		for(int i = 0; i < listOfPegawai.size(); i++) {
			if(i+1 != listOfPegawai.size()) {
				if(	tua.getTanggalLahir().before(listOfPegawai.get(i+1).getTanggalLahir())) {
					tua = listOfPegawai.get(i+1);
				}
			}
		}
		System.out.println(muda.getJabatanList());
		System.out.println(tua.getJabatanList());
		model.addAttribute("tua", tua);
		model.addAttribute("muda", muda);
		return "viewTertuaTermuda";
	}
}
