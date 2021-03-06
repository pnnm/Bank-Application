package com.service.thinkProBranch.serviceImpl;

import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.thinkProBranch.domain.Branch;
import com.service.thinkProBranch.repository.AddressRepository;
import com.service.thinkProBranch.repository.BankRepository;
import com.service.thinkProBranch.repository.BranchRepository;
import com.service.thinkProBranch.service.BranchService;

@Component
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepository;

	@SuppressWarnings("unused")
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AddressRepository addressRepository;
	@Transactional
	public Branch getBranchInfo(int branchCode) {
		return branchRepository.getOne(branchCode);
	}

	@Transactional
	public String getBranchById(int branchCode){
		String str = branchRepository.getOne(branchCode).toString();
		JsonParser jsonParser = new JsonParser();
		JsonObject objectFromString = jsonParser.parse(str).getAsJsonObject();
		System.out.println("BranchDetails---->" + objectFromString);
		return String.valueOf(objectFromString);
	}

	@Transactional
	public boolean saveBranch(Branch branch) {
		return branchRepository.save(branch) != null;
	}

	@Transactional
	public boolean updateBranch(Branch branch) {
        addressRepository.save(branch.getAddress());

		return branchRepository.save(branch) != null;
	}

	@Transactional
	public Collection<Branch> getAllBranches() {
		return branchRepository.findAllActiveUsersNative();
	}

	@Transactional
	public void deleteBranch(int branchCode) throws InterruptedException {
		Branch branch = branchRepository.getOne(branchCode);
		int addressID = branch.getAddress().getAddressId();
		branchRepository.deleteAddressId(branchCode);
		addressRepository.deleteById(addressID);
		branchRepository.deleteBranch(branchCode);
	}

}
