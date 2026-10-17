package com.pupr.Config;

import com.pupr.Master.*;
import com.pupr.Param.TugasanParamService;
import com.pupr.Param.TugasanParamServiceImplement;
import com.pupr.Transaction.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.PushBuilder;
import java.security.PublicKey;

@Configuration
public class TransactionBeanConfig {

    @Bean
    public MasterServices masterServices(){
        return new MasterServicesImpl();
    }

    @Bean
    public MasterPegawaiService masterPegawaiService(){
        return new MasterPegawaiServicesImpl();
    }

    @Bean
    public MasterMitraService masterMitraService(){
        return new MasterMitraServiceImpl();
    }

    @Bean
    public BapServices bapServices(){ return new BapServiceImpl();
    }

    @Bean
    public KwitansiServices kwitansiServices() {return new KwitansiServiceImpl();
    }

    @Bean
    public RingkasanService ringkasanService() { return new RingkasanSeviceImpl();}

    @Bean
    public UserMasterServices userMasterServices(){return new UserMasterServicesImpl();}

    @Bean
    public SppServices sppServices(){return new SppServicesImpl();}

    @Bean
    public SpmServices spmServices(){return new SpmServicesImpl();}

    @Bean
    public Sp2dServices sp2dServices(){return new Sp2dServicesImpl();}

    @Bean
    public RekomServices rekomServices(){ return  new RekomServiceImpl();}

    @Bean
    public DeleteCollectionServices collectionServices(){return new DeleteCollectionSvcImpl();}

    @Bean
    public TugasanParamService tugasanParamService(){return new TugasanParamServiceImplement();
    }

}
