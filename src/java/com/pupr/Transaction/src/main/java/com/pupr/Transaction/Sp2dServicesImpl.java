package com.pupr.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Master.DeletedCollection;
import com.pupr.Master.DeletedCollectionDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class Sp2dServicesImpl implements Sp2dServices {

    @Autowired
    Sp2dDaoServices sp2dDaoServices;

    @Autowired
    BapServices bapServices;

    @Autowired
    BapDaoServices bapDaoServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Override
    public Sp2D submitData(Sp2D payload) {
        payload.setCreatedDate(LocalDate.now().toString());
        payload.setCreatedTime(LocalDateTime.now());
        payload.setIsDeleted(false);

        System.out.println("Ini No SP2D " + payload.getSp2dNo());

        Sp2D sp2DByBapNo = sp2dDaoServices.findByBapNo(payload.getBapNo());
        Sp2D sp2DBySppNo = sp2dDaoServices.findBySp2dNo(payload.getSp2dNo());

        if(sp2DByBapNo == null && sp2DBySppNo == null){
            payload.setSp2dDateText(formatterV1.format(payload.getSp2dDate()));
            sp2dDaoServices.submitData(payload);
            bapServices.bapUpdateChildTrxId(Long.valueOf(payload.getBapId()),payload.getId(),TRX_CODE_SP2D);
            return payload;
        }else{
            throw new ConflictException("Data Duplicated");
        }
    }

    @Override
    public Sp2D submitRemoveData(Sp2D payload) {
        Optional<Sp2D> sp2dDB = sp2dDaoServices.findById(payload.getId());

        if(sp2dDB.isPresent()) {
            Sp2D sp2D = sp2dDB.get();
            sp2D.setDeletedBy(payload.getDeletedBy());
            sp2D.setDeletedDate(LocalDate.now().toString());
            sp2D.setDeletedTime(LocalDateTime.now());
            sp2D.setIsDeleted(true);
            return sp2dDaoServices.submitData(sp2D);
        }else{
            return null;
        }
    }

    @Override
    public Sp2D findBySp2dNo(String sp2dNo) {
        return sp2dDaoServices.findBySp2dNo(sp2dNo);
    }

    @Override
    public Sp2D findByBapNo(String bapNo) {
        return sp2dDaoServices.findByBapNo(bapNo);
    }

    @Override
    public DeletedCollection removeById(Map<String, String> params) {

        DeletedCollection deletedCollection = new DeletedCollection();
        String bapid = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapid));
        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_SP2D);
            deletedCollection.setValueTrx(String.valueOf(bap.getKwitansi()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            sp2dDaoServices.RemoveById(bap.getSp2D().getId());
        }

        return deletedCollection;
    }

    @Override
    public List<Sp2D> getSppTrxByDatePeriode(Pageable pageable, Map<String, String> params) {

        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        List<Sp2D> sp2DList = sp2dDaoServices.findSp2dByDatePeriode(pageable,startDate,endDate);


        return sp2DList;
    }
}
