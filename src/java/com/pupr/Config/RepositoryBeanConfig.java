package com.pupr.Config;

import com.pupr.Master.*;
import com.pupr.MasterParam.TugasanParamDaoService;
import com.pupr.MasterParam.TugasanParamDaoServiceImpl;
import com.pupr.SystemParam.BidangTahunActiveDaoService;
import com.pupr.SystemParam.BidangTahunActiveDaoSvcImpl;
import com.pupr.Transaction.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.PushBuilder;

@Configuration
public class RepositoryBeanConfig {

    @Bean
    public MasterPegawaiDaoService masterPegawaiDaoService() { return new MasterPegawaoDaoServcImpl();}

    @Bean
    public MasterDaoService masterDaoService(){
        return new MasterDaoServiceImpl();
    }

    @Bean
    public MasterMitraDaoServices masterMitraDaoServices(){
        return new MasterMitraDaoServiceImpl();
    }

    @Bean
    public BapDaoServices bapDaoServices (){return new BapDaoServiceImpl();
    }

    @Bean
    public KwitansiDaoServices kwitansiDaoServices(){return new KwitansiDaoServiceImnpl();
    }

    @Bean
    public RingkasanDaoService ringkasanDaoService() { return new RingkasanDaoServiceImpl();}

    @Bean
    public  UserMasterDaoService userMasterDaoService(){return new UserMasterDaoSerciveImpl();}

    @Bean
    public SppDaoServices sppDaoServices(){return new SppDaoSericesImpl();}

    @Bean
    public SpmDaoServices spmDaoServices(){return new SpmDaoServicesImpl();}

    @Bean
    public Sp2dDaoServices sp2dDaoServices(){return new Sp2dDaoSercicesImpl();}

    @Bean
    public RekomDaoServices rekomDaoServices(){return new RekomDaoSvcImpl();}

    @Bean
    public ValidasiDaoService validasiDaoService(){return  new ValidasiDaoServicesImpl();}

    @Bean
    public DeletedCollectionDaoServices collectionDaoServices(){return  new DeleteCollectionDaoSvcImpl();}

    @Bean
    public TugasanParamDaoService tugasanParamDaoService(){return new TugasanParamDaoServiceImpl();
    }

    @Bean
    public BidangTahunActiveDaoService bidangTahunActiveDaoService(){
        return new BidangTahunActiveDaoSvcImpl();
    }

}
