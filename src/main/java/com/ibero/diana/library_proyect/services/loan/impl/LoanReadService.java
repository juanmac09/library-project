package com.ibero.diana.library_proyect.services.loan.impl;

import com.ibero.diana.library_proyect.dtos.loan.LoanDto;
import com.ibero.diana.library_proyect.entities.Loan;
import com.ibero.diana.library_proyect.enums.loan.LoanState;
import com.ibero.diana.library_proyect.mapping.IMapper;
import com.ibero.diana.library_proyect.repositories.loan.LoanRepository;
import com.ibero.diana.library_proyect.services.loan.ILoanReadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class LoanReadService implements ILoanReadService {

    private final LoanRepository loanRepository;
    private final IMapper<Loan, LoanDto> loanMapper;

    public LoanReadService(LoanRepository loanRepository,
                           IMapper<Loan, LoanDto> loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public Page<LoanDto> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable).map(this.loanMapper::map);
    }

    @Override
    public LoanDto getLoanById(int id) {
        return loanRepository.findById(id)
                .map(loanMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado con id: " + id));
    }

    @Override
    public Page<LoanDto> getLoansByUser(int userId, Pageable pageable) {
        return loanRepository.findByUser_Id(userId, pageable).map(loanMapper::map);
    }

    @Override
    public Page<LoanDto> getLoansByBook(int bookId, Pageable pageable) {
        return loanRepository.findByBook_Id(bookId, pageable).map(loanMapper::map);
    }

    @Override
    public Page<LoanDto> getLoansByState(LoanState state, Pageable pageable) {
        return loanRepository.findByState(state, pageable)
                .map(loanMapper::map);
    }
}
