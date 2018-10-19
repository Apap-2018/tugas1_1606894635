package com.apap.tugas1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;

@Service
public interface InstansiService {

	InstansiModel getInstansiById(long instansiId);

}
