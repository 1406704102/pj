package com.pangjie.obs;

import com.obs.services.ObsClient;
import lombok.Data;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Component
@Data
//@PropertySource("config/application-obs.yml")
//@ConfigurationProperties(prefix = "OBS")
public class HuaWeiObs {

    @Value("${OBS.END_POINT}")
    private String END_POINT;
    @Value("${OBS.BUCKET_NAME}")
    private String BUCKET_NAME;
    @Value("${OBS.AK}")
    private String AK;
    @Value("${OBS.SK}")
    private String SK;
    @Value("${OBS.URL}")
    private String URL;

    /**
     * 功能描述:  上传文件到华为obs
     * No such property: code for class: Script1
     *
     * @return: 文件访问路径
     * @Author: pangjie
     * @Date: 2020/2/26 0026 17:08
     */
    public String updateFile(MultipartFile file, String fileName, String folder) throws IOException {
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        //图片压缩
        Thumbnails.of(inputStream).scale(1f).outputQuality(1f).toOutputStream(os);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(os.toByteArray());
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(AK, SK, END_POINT);
        // 上传文件，注意：上传内容大小不能超过5GB
        String url = folder + "/" + fileName + "." + suffix;
        obsClient.putObject(BUCKET_NAME, url, byteArrayInputStream);
        obsClient.close();
        return URL + url;
    }

    /**
     * 功能描述: 删除obs中的文件
     * No such property: code for class: Script1
     * @return:
     * @Author: pangjie
     * @Date: 2020/4/1 0001 17:16
     */
    public void deleteFile(String fileName){
        ObsClient obsClient = new ObsClient(AK, SK, END_POINT);
        obsClient.deleteObject(BUCKET_NAME, fileName);
    }
}
