package com.apap.tugas1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;

@Service
public interface JabatanService{
	List<JabatanModel> getListJabatan();
	JabatanModel findJabatanById(long idJabatan);
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan);
	void deleteJabatan(long idJabatan);
	
}
