package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public List<JabatanModel> getListJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel findJabatanById(long idJabatan) {
		return jabatanDb.getOne(idJabatan);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel jabatanUpdate = jabatanDb.findById(jabatan.getId()).get();
		
		jabatanUpdate.setNama(jabatan.getNama());
		jabatanUpdate.setDeskripsi(jabatan.getDeskripsi());
		jabatanUpdate.setGajiPokok(jabatan.getGajiPokok());
		
		jabatanDb.save(jabatanUpdate);
	}
	
	public void deleteJabatan(long idJabatan) {
		jabatanDb.deleteById(idJabatan);
	}
}
