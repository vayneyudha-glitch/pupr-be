package com.pupr.Transaction;

import com.pupr.Aop.ValidateTahunDb;
import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.KwitansiResponse;
import com.pupr.Master.*;
import com.pupr.Util.CommonFunction;
import net.sf.jasperreports.engine.*;

import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class KwitansiServiceImpl implements KwitansiServices{

    @Autowired
    KwitansiDaoServices kwitansiDaoServices;

    @Autowired
    BapServices bapServices;

    @Autowired
    RingkasanService ringkasanService;

    @Autowired
    MasterMitraService masterMitraService;

    @Autowired
    MasterDaoService masterDaoService;

    @Autowired
    BapDaoServices bapDaoServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    private static final String[] units = {
            "", "Satu", "Dua", "Tiga", "Empat", "Lima",
            "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"
    };

    @Override
    @Transactional
    @ValidateTahunDb
    public KwitansiResponse submit(Kwitansi kwitansi) throws Exception {
        Kwitansi kwitansiResult = new Kwitansi();
        KwitansiResponse kwitansiResponse = new KwitansiResponse();

        if (kwitansi.getId() == null){
            kwitansi.setCreatedDate(LocalDate.now().toString());
            kwitansi.setCreatedTime(LocalDateTime.now());

            Kwitansi kwitansiDb =kwitansiDaoServices.findByBapNumber(kwitansi.getBapNo());
            if (kwitansiDb != null){
                 throw new ConflictException("Data Duplicated");
            }

            bapServices.bapUpdateChildTrxId(Long.valueOf(kwitansi.getBapId()),kwitansi.getId(),TRX_CODE_KWITANSI);
        }else {

            Kwitansi kwitansiDb = kwitansiDaoServices.findById(kwitansi.getId());
            kwitansi.setCreatedDate(kwitansiDb.getCreatedDate());
        }

        kwitansi.setLastUpdate(LocalDate.now().toString());
        kwitansi.setLastUpdateTime(LocalDateTime.now());
        kwitansi.setIsDeleted(false);

        kwitansiResult = kwitansiDaoServices.submit(kwitansi);
        kwitansiResponse.setKwitansi(kwitansi);
        return kwitansiResponse;
    }

    @Override
    public KwitansiResponse findById(Long id) throws Exception {
        Ringkasan ringkasanDb = new Ringkasan();
        MasterMitra masterMitra = new MasterMitra();
        KwitansiResponse kwitansiResponse = new KwitansiResponse();
        Kwitansi kwitansi = kwitansiDaoServices.findById(id);

        if (kwitansi == null){
            return null;
        }else{
            kwitansiResponse.setKwitansi(kwitansi);
            BapResponse bapResponse = bapServices.findById(Long.valueOf(kwitansi.getBapId()));

            if (bapResponse != null){
                kwitansiResponse.setBap(bapResponse.getBap());
                kwitansiResponse.setMaster(bapResponse.getMaster());

                ringkasanDb = ringkasanService.findByBapId(String.valueOf(bapResponse.getBap().getId()));
                if(ringkasanDb.getVendorId() != null){
                    masterMitra =  masterMitraService.findById(Long.valueOf(ringkasanDb.getVendorId()));
                    kwitansiResponse.setMasterMitra(masterMitra);
                }
            }
        }



        return kwitansiResponse;
    }

    @Override
    public List<Kwitansi> findByBapId(String id) throws Exception {
        return kwitansiDaoServices.findByBapId(id);
    }

    @Override
    public List<Kwitansi> findByLimit1(Pageable pageable, Map<String,String> params) {
        List<Kwitansi> kwitansiListResult = new ArrayList<>();

        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        List<Kwitansi> kwitansiList = kwitansiDaoServices.findDataLImit1(pageable,startDate,endDate);

        return kwitansiList;
    }

    @Override
    public List<Bap> findByLimit1V2(Pageable pageable, Map<String, String> params) {

        List<Bap> bapResult = new ArrayList<>();

        String bapNo = params.get("bapNo");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        List<Bap> bap = bapDaoServices.findDataBapDate(pageable,startDate,endDate);

        if(bapNo != null){
            bapResult = bapDaoServices.findByBapNoV2(bapNo,START_FROM_TAHUN_ANGGARAN);
        }else{
            if(bidang != null){
                bapResult = bap.stream()
                        .filter(b -> b.getMaster().getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }else{
                bapResult = bap.stream()
                        .collect(Collectors.toList());
            }

            //Ini untuk mencari BAP yang benar2 ada List Kwitansi
            bapResult = bapResult.stream()
                    .filter(kwitansi -> kwitansi.getKwitansi() != null)
                    .collect(Collectors.toList());
        }

        return bapResult;
    }

    private KwitansiResponse mapperFromKwitansiToKwitansiResponse(Kwitansi kwitansi){
        KwitansiResponse response = new KwitansiResponse();

        response.setBap(bapDaoServices.findByBapNo(kwitansi.getBapNo()));
        response.setKwitansi(kwitansi);

        return response;
    }

    @Override
    public List<Kwitansi> findByBapNo(String bapNo) throws Exception {
        return kwitansiDaoServices.findByBapNoLike(bapNo);
    }

    @Override
    public byte[] generateFaktur(Long id) throws Exception {
        String StaticNipWord = "NIP. ";
        Kwitansi kwitansi = kwitansiDaoServices.findById(id);
        BapResponse response = bapServices.findByBapNo(kwitansi.getBapNo());
        MasterMitra mitra = masterMitraService.findById(Long.valueOf(response.getRingkasan().getMitraId()));

        System.out.println("rsponse " + response.getRingkasan().getMitraId());

        byte[] reportContent;

        JasperReport jasperReport;
        JasperPrint jasperPrint = null;

        try {
            jasperReport = (JasperReport)
                    JRLoader.loadObject(ResourceUtils.getFile("Kwitansi-Faktur.jasper"));
        } catch (FileNotFoundException | JRException e) {
            try {
//                File file = ResourceUtils.getFile("classpath:Kwitansi-Faktur.jrxml");
//                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                InputStream employeeReportStream
                        = getClass().getResourceAsStream("/Kwitansi-Faktur.jrxml");
                jasperReport = JasperCompileManager.compileReport(employeeReportStream);


                JRSaver.saveObject(jasperReport, id.toString());
            } catch (JRException ex) {
                System.out.println("Ini Exception " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }

        String bapAmount = CommonFunction.mapperBigDecimalToStringCurrencyFormat(response.getBap().getBapAmount() != null ? response.getBap().getBapAmount() : new BigDecimal(0));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sumberAnggaran", "PEMERINTAH DAERAH KABUPATEN SUBANG");
        int number  = response.getBap().getBapAmount().intValue();
        System.out.println("Nilai " + number + " - " + response.getBap().getBapAmount());
        parameters.put("terbilang", getRupiahTerbilang(response.getBap().getBapAmount().longValue()) + " Rupiah");
        parameters.put("kodeRekening", response.getMaster().getKodeSubKegiatan() + response.getMaster().getKodeBelanja());
        parameters.put("desc", response.getKwitansi().getDescription() != null ? response.getKwitansi().getDescription() : "-");
        parameters.put("bapAmount", bapAmount !=null ? bapAmount : "-");

        String bidangTitle = StringUtils.capitalize(response.getMaster().getBidang());

        System.out.println("Ini Master Id " + response.getMaster().getId());

        parameters.put("bidang", "Kepala Bidang " + bidangTitle);
        parameters.put("namaKepalaBidang", response.getKwitansi().getKepalaBidangName() != null ? response.getKwitansi().getKepalaBidangName() : "-");
        parameters.put("nipKepalaBidang", response.getKwitansi().getKepalaBidangNo() != null ? StaticNipWord +"" +  response.getKwitansi().getKepalaBidangNo() :  StaticNipWord);
        parameters.put("namaPpk", response.getKwitansi().getPpkName() !=null ?  response.getKwitansi().getPpkName() : "-");
        parameters.put("nipPpk", response.getKwitansi().getPpkNo() != null ? StaticNipWord +"" + response.getKwitansi().getPpkNo():StaticNipWord);
        parameters.put("namaPptk", response.getKwitansi().getPptkName() != null ?  response.getKwitansi().getPptkName() : "-");
        parameters.put("nipPptk", response.getKwitansi().getPptkNo() != null ? StaticNipWord +"" + response.getKwitansi().getPptkNo() : StaticNipWord);
        parameters.put("namaBpp", response.getKwitansi().getBppName() != null ? response.getKwitansi().getBppName() : "-" );
        parameters.put("nipBpp", response.getKwitansi().getBppNo() != null ? StaticNipWord +"" + response.getKwitansi().getBppNo() : StaticNipWord);

        if(mitra != null) {
            parameters.put("namaPerusahaan", mitra.getCorporateName() != null ?  mitra.getCorporateName() : "-");
            parameters.put("namaDirektur", mitra.getHeadOffice() != null ? mitra.getHeadOffice() : "-");
            parameters.put("nipDirektur", "-");
        }else{
            parameters.put("namaPerusahaan", "-");
            parameters.put("namaDirektur", "-");
            parameters.put("nipDirektur", "-");
        }

        String kwitansiDate = response.getKwitansi().getKwitansiDate() != null ?  formatterReport.format(response.getKwitansi().getKwitansiDate()) : "-";

        parameters.put("tglkwitansi", "Subang, " + kwitansiDate);
        System.out.println("Isi Kwitansi " + String.valueOf(response.getKwitansi().getKwitansiDate()));

        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
        }catch (JRException e){
            System.out.println( "JRE Exception" + e.getMessage());
            throw new RuntimeException();
        }
        return reportContent;
    }

    @Override
    public Kwitansi removeById(Long id) throws Exception {

        Kwitansi kwitansiDb = kwitansiDaoServices.findById(id);
        List<Bap> bap = bapServices.findByBapNoV2(kwitansiDb.getBapNo());
        if(bap.size() > 0 && bap.get(0).getKwitansi() == null){
            kwitansiDb.setDeletedDate(LocalDate.now().toString());
            kwitansiDb.setDeletedTime(LocalDateTime.now());
            kwitansiDb.setIsDeleted(true);
            kwitansiDaoServices.submit(kwitansiDb);
            return kwitansiDb;
        }else{
            throw new Exception("Data Can't Be Removed");
        }
    }

    @Override
    public DeletedCollection removeById2(Map<String, String> params) {

        DeletedCollection deletedCollection = new DeletedCollection();
        String bapid = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapid));
        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_KWITANSI);
            deletedCollection.setValueTrx(String.valueOf(bap.getKwitansi()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            kwitansiDaoServices.removeById(bap.getKwitansi().getId());
        }

        return deletedCollection;
    }

    private String getRupiahTerbilang(long number){

        final String[] angka = {
                "", "Satu", "Dua", "Tiga", "Empat", "Lima",
                "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"
        };

        if (number < 12) {
            return angka[(int) number];
        } else if (number < 20) {
            return getRupiahTerbilang(number - 10) + " Belas";
        } else if (number < 100) {
            return getRupiahTerbilang(number / 10) + " Puluh " + getRupiahTerbilang(number % 10);
        } else if (number < 200) {
            return "Seratus " + getRupiahTerbilang(number - 100);
        } else if (number < 1000) {
            return getRupiahTerbilang(number / 100) + " Ratus " + getRupiahTerbilang(number % 100);
        } else if (number < 2000) {
            return "Seribu " + getRupiahTerbilang(number - 1000);
        } else if (number < 1000000) {
            return getRupiahTerbilang(number / 1000) + " Ribu " + getRupiahTerbilang(number % 1000);
        } else if (number < 1000000000) {
            return getRupiahTerbilang(number / 1000000) + " Juta " + getRupiahTerbilang(number % 1000000);
        } else {
            return getRupiahTerbilang(number / 1000000000) + " Miliar " + getRupiahTerbilang(number % 1000000000);
        }

    }
}
