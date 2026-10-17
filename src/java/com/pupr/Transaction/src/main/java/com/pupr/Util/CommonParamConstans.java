package com.pupr.Util;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class CommonParamConstans {


    public static final SimpleDateFormat formatterV1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat formatterReport = new SimpleDateFormat("dd-MM-yyyy");
    public static final DecimalFormat df = new DecimalFormat("#,###");

    public static final String IP_MASTER_SERVICE = "http://103.16.116.103:8083";

//    For Local SetUp
//    public static final String IP_MASTER_SERVICE = "http://localhost:8083";

    public static final Integer TAHUN_ANGGARAN = 2026;
    public static final String BULAN_DATE_ANGGARAN = "-01-01";
    public static final String START_FROM_TAHUN_ANGGARAN = TAHUN_ANGGARAN + BULAN_DATE_ANGGARAN;

    public final static String EMPLOYMENT_NAME = "name";
    public final static String EMPLOYMENT_NPWP = "npwp";

    public final static String TRX_CODE_SPK = "SPK";
    public final static String TRX_CODE_BAP = "BAP";
    public final static String TRX_CODE_RINGKASAN = "RINGKASAN";
    public final static String TRX_CODE_NPD = "NPD";
    public final static String TRX_CODE_KWITANSI = "KWITANSI";
    public final static String TRX_CODE_SPP = "SPP";
    public final static String TRX_REKOM = "REKOM";
    public final static String TRX_CODE_SPM = "SPM";
    public final static String TRX_CODE_VALIDASI = "VALIDASI";
    public final static String TRX_CODE_SP2D = "SP2D";

}
