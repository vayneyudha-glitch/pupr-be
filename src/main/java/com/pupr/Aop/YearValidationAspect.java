package com.pupr.Aop;

import com.pupr.Master.MasterDaoService;
import com.pupr.SystemParam.BidangTahunActive;
import com.pupr.SystemParam.BidangTahunActiveDaoService;
import com.pupr.SystemParam.BidangTahunActiveRepo;
import com.pupr.Transaction.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Aspect
@Component
@Order(1)
public class YearValidationAspect {

    @Autowired
    private MasterDaoService masterDaoService;

    @Autowired
    private BidangTahunActiveDaoService bidangTahunActiveDaoService;

    private static final Map<Class<?>, Function<Object, String>> EXTRACTORS = new HashMap<>();

    static {
        EXTRACTORS.put(Bap.class, p -> ((Bap) p).getMasterTransactionId());
        EXTRACTORS.put(Ringkasan.class, p -> ((Ringkasan) p).getMasterTransactionId());
        EXTRACTORS.put(Kwitansi.class, p -> ((Kwitansi) p).getMasterTransactionId());
        EXTRACTORS.put(Spp.class, p -> ((Spp) p).getMasterTransactionId());
        EXTRACTORS.put(String.class, p -> (String) p);
    }

    @Around("@annotation(ValidateTahunDb)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isValidateTrue = true;

        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] != null) {
            Object payload = args[0];

            Function<Object, String> extractor = EXTRACTORS.get(payload.getClass());
            if (extractor != null) {
                String masterId = extractor.apply(payload);
                ValidTahunAnggaranForSubmit(masterId);
            }
        }

        return joinPoint.proceed();
    }

    private void ValidTahunAnggaranForSubmit(String masterTrxId){

        boolean isValid = false;
        String masterId = masterTrxId;

        if (masterId != null) {
            Optional<Master> masterOpt = masterDaoService.getById(Long.valueOf(masterId));

            if(masterOpt.isPresent()){
                Optional<BidangTahunActive> bidangTahunActive = bidangTahunActiveDaoService
                        .findByBidangName(masterOpt.get().getBidang());

                if(bidangTahunActive.isPresent()){
                    String dbTahun = bidangTahunActive.get().getTahunActive();
                    if (dbTahun != null && dbTahun.equalsIgnoreCase(masterOpt.get().getTahun().toString())) {
                        isValid = true;
                    }
                }

            }
        }

        if(!isValid){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Transaction Blocked: Cannot modify data for year ");
        }
    }
}
