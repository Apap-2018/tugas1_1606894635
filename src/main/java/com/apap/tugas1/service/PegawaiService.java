package com.apap.tugas1.service;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PegawaiModel;

@Service
public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	void searchPegawai(long id);
	void updatePegawai(PegawaiModel pegawai, long idPegawai);
}
