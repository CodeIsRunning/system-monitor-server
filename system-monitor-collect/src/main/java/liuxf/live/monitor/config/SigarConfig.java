package liuxf.live.monitor.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

/**
 * @author liuxf
 * @version 1.0
 * @date 2020/11/4 9:55
 */
@Slf4j
@Configuration
public class SigarConfig {

    //静态代码块
    static {
        try {
            initSigar();
        } catch (IOException e) {
            e.printStackTrace();
            log.info(ExceptionUtils.getStackTrace(e));
        }
    }

    //初始化sigar的配置文件
    public static void initSigar() throws IOException {
        SigarLoader loader = new SigarLoader(Sigar.class);
        String lib = null;

        try {
            lib = loader.getLibraryName();
            log.info("init sigar so文件=====================" + lib);
        } catch (ArchNotSupportedException var7) {
            log.error("initSigar() error:{}", var7.getMessage());
        }
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/so/" + lib);
        if (resource.exists()) {
            InputStream is = resource.getInputStream();
            File tempDir = new File("/var/log");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int lentgh = 0;
            while ((lentgh = is.read()) != -1) {
                os.write(lentgh);
            }

            is.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
        }

    }
}

