package com.offcn.controller;

import com.offcn.pojo.*;
import com.offcn.service.AppCategoryService;
import com.offcn.service.AppInfoService;
import com.offcn.service.AppVersionService;
import com.offcn.service.DataDictionaryService;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private AppCategoryService appCategoryService;
    @Autowired
     private AppVersionService appVersionService;
    List<AppCategory> CategoryLevel1=null;
    List<DataDictionary> statusList=null;
    List<DataDictionary> flatFormList=null;
    List<AppCategory> list=null;
    @RequestMapping("/list")
    public String getAppInfo(HttpSession session, @RequestParam(value = "queryStatus",required = false) String status,
                             @RequestParam(value = "queryFlatformId",required = false) String flatformId,
                             @RequestParam(value = "queryCategoryLevel1",required = false) String categoryLevel1,
                             @RequestParam(value = "queryCategoryLevel2",required = false) String categoryLevel2,
                             @RequestParam(value = "queryCategoryLevel3",required = false) String categoryLevel3,
                             @RequestParam(value = "querySoftwareName",required = false) String softwareName,
                             @RequestParam (value = "pageIndex",required = false) String currentPageNo,
                             @RequestParam(value = "pageSize",required = false) String pageSize, Model model){
        AppInfo info=new AppInfo();
        Integer status1=null;
        if (status!=null&&!"".equals(status)){
            status1 = Integer.parseInt(status);
        }
        info.setStatus(status1);
        Integer flatformId1=null;
        if (flatformId != null&&!"".equals(flatformId)) {
            flatformId1= Integer.parseInt(flatformId);
        }
        info.setFlatformId(flatformId1);
       Integer categoryLevel11=null;
        if (categoryLevel1 != null&&!"".equals(categoryLevel1)) {
            categoryLevel11= Integer.parseInt(categoryLevel1);
        }
        info.setCategoryLevel1(categoryLevel11);
        Integer categoryLevel21=null;
        if (categoryLevel2 != null&&!"".equals(categoryLevel2)) {
            categoryLevel21= Integer.parseInt(categoryLevel2);
        }
        info.setCategoryLevel2(categoryLevel21);
        Integer categoryLevel31=null;
        if (categoryLevel3 != null&&!"".equals(categoryLevel3)) {
            categoryLevel31= Integer.parseInt(categoryLevel3);
        }
        info.setCategoryLevel3(categoryLevel31);
        DevUser userSession = (DevUser) session.getAttribute("userSession");
        Integer id = userSession.getId();
        info.setDevId(id);
        info.setSoftwareName(softwareName);
        Page appInfo = appInfoService.getAppInfo(info, pageSize, currentPageNo);
        List<AppInfo> infoList = appInfo.getInfoList();
        model.addAttribute("pages",appInfo);
        model.addAttribute("appInfoList",infoList);
        statusList = dataDictionaryService.getByTypeCode("APP_STATUS");
        flatFormList=dataDictionaryService.getByTypeCode("APP_FLATFORM");
        model.addAttribute("statusList",statusList);
        model.addAttribute("flatFormList",flatFormList);
        CategoryLevel1= appCategoryService.getCategoryById(null);
        model.addAttribute("categoryLevel1List",CategoryLevel1);
        model.addAttribute("queryStatus",status1);
        model.addAttribute("queryFlatformId",flatformId1);
        model.addAttribute("querySoftwareName",softwareName);
        return "/developer/appinfolist";
    }
    @RequestMapping("/categorylevellist.json")
    @ResponseBody
    public List<AppCategory> getCategoryLevel2ById(@RequestParam("pid") String id){

        if (id==null||"".equals(id)){
            list=appCategoryService.getCategoryById(null);
        }else {
           list= appCategoryService.getCategoryById(Integer.parseInt(id));
        }
        return list;
    }
    @RequestMapping("/appview/{id}")
    public String lookAppVer(@PathVariable("id") String id,Model model){
        if (id!=null&&!"".equals(id)){
            AppInfo byId = appInfoService.getById(Integer.parseInt(id),null);
            model.addAttribute("appInfo",byId);
            Integer id1 = byId.getId();
            List<AppVersion> byAppId = appVersionService.getByAppId(id1);
            model.addAttribute("appVersionList",byAppId);
        }
      return "/developer/appinfoview";

    }
    @RequestMapping("/datadictionarylist.json")
    @ResponseBody
    public List<DataDictionary> getDataDictionary(@RequestParam(value = "tcode",required = false) String tcode){
        List<DataDictionary> app_flatform = dataDictionaryService.getByTypeCode(tcode);
        return app_flatform;
    }
    @RequestMapping("apkexist.json")
    @ResponseBody
    public Object getApkName(@RequestParam(value = "APKName",required = false) String apkname) {
        HashMap<String, Object> map = new HashMap<>();
        if (apkname == null || "".equals(apkname)) {
            map.put("APKName", "empty");
        } else if (appInfoService.getById(null,apkname)!=null) {
            map.put("APKName", "exist");
        } else {
            map.put("APKName", "noexist");
        }
        return map;
    }
    //spring 提供的上传工具类MultipartFile
    @RequestMapping("/appinfoaddsave")
    public String addinfo(AppInfo appInfo,
                          @RequestParam(value = "a_logoPicPath",required = false)MultipartFile attach,
                          HttpSession session, HttpServletRequest request){
          //标记
        boolean flag=false;
        //获取上传服务器地址
        String path=request.getSession().getServletContext().getRealPath("statics/uploadfiles/");

        //首先判断类型是否合法
        if(!attach.isEmpty()){
            //获取原文件
            String filename = attach.getOriginalFilename();
            //判断文件何种类型
            String extension = FilenameUtils.getExtension(filename);
            //文件大小
            int fileSize=5000000;
            if(filename.length()>fileSize){
                request.setAttribute("error","文件过大，服务器不支持");
            }else if (!(extension.equalsIgnoreCase("png")
                    ||extension.equalsIgnoreCase("jpg")
                    ||extension.equalsIgnoreCase("jpeg"))){
                request.setAttribute("error","文件格式不支持");
            }else {
                //唯一文件名称
                String filePathName=appInfo.getAPKName()+"."+extension;
                //得到绝对路径
                String localPicPath=path+filePathName;
                //得到相对路径
                String logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+filePathName;
                //把文件写入服务器
                File file=new File(localPicPath);
                if (!file.exists()){
                    file.mkdir();
                }
                try {
                    attach.transferTo(file);
                    flag=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (flag){
                    appInfo.setDevId(((DevUser)session.getAttribute("userSession")).getId());
                    appInfo.setStatus(1);
                    appInfo.setCreatedBy(1);
                    appInfo.setCreationDate(new Date());
                    appInfo.setLogoPicPath(logoPicPath);
                    appInfo.setLogoLocPath(localPicPath);
                    if (appInfoService.addInfo(appInfo)>0){
                        return "redirect: list ";

                    }
                }else {
                    //得到当前图片在服务器的地址；

                }
            }
        }
        return "redirect: appinfoadd";
    }
    //修改的app的信息
    @RequestMapping("/appinfomodify")
    public String updateAppInfo(@RequestParam(value = "id",required = false)String id,Model model){
        //根据id来进行查询
        AppInfo appInfo = appInfoService.getById(Integer.parseInt(id),null);
        model.addAttribute("appInfo",appInfo);
        return "developer/appinfomodify";
    }
    @ResponseBody
    @RequestMapping("/delfile.json")
    public Object delfileLogo(@RequestParam(value = "id",required = false) String id,@RequestParam(value = "flag",required = false) String flag){
        Map<String,Object> map=new HashMap<>();
        if (id==null||"".equals(id)||flag==null||"".equals(flag)){
            map.put("result","failed");
        }else if (flag.equals("logo")){
            AppInfo byId = appInfoService.getById(Integer.parseInt(id), null);
            String logoLocPath = byId.getLogoLocPath();
            File file=new File(logoLocPath);
            if (file.exists()){
                //删除文件
                //文件地址清空
                Integer integer = appInfoService.updateLogopath(byId);
                if (integer>0){
                    boolean delete = file.delete();
                    map.put("result","success");
                }else {
                    map.put("result","failed");
                }
            }
        }else if (flag.equals("sql")){
            AppVersion appVersionByVid = appVersionService.getAppVersionByVid(Integer.parseInt(id));
            Integer integer = appVersionService.updateAppVersionLocalByVid(appVersionByVid);
            //删除成功
            if (integer>0){
                //删除文件
                File file=new File(appVersionByVid.getApkLocPath());
                if (file.exists()){
                    file.delete();
                    map.put("result","success");
                }else {
                    map.put("result","failed");
                }
            }
        }
        return map;
    }
    @RequestMapping("/appinfomodifysave")
    public String updateAppIfo(AppInfo appInfo,
                               @RequestParam(value = "attach",required = false)MultipartFile attach,
                               HttpSession session, HttpServletRequest request){
        //标记
        boolean flag=false;
        //获取上传服务器地址
        String path=request.getSession().getServletContext().getRealPath("statics/uploadfiles/");

        //首先判断类型是否合法
        if(!attach.isEmpty()){
            //获取原文件
            String filename = attach.getOriginalFilename();
            //判断文件何种类型
            String extension = FilenameUtils.getExtension(filename);
            //文件大小
            int fileSize=5000000;
            if(filename.length()>fileSize){
                request.setAttribute("error","文件过大，服务器不支持");
            }else if (!(extension.equalsIgnoreCase("png")
                    ||extension.equalsIgnoreCase("jpg")
                    ||extension.equalsIgnoreCase("jpeg"))){
                request.setAttribute("error","文件格式不支持");
            }else {
                //唯一文件名称
                String filePathName=appInfo.getAPKName()+"."+extension;
                //得到绝对路径
                String localPicPath=path+filePathName;
                //得到相对路径
                String logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+filePathName;
                //把文件写入服务器
                File file=new File(localPicPath);
                if (!file.exists()){
                    file.mkdir();
                }
                try {
                    attach.transferTo(file);
                    flag=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (flag){
                    appInfo.setDevId(((DevUser)session.getAttribute("userSession")).getId());
                    appInfo.setStatus(1);
                    appInfo.setModifyBy(1);
                    appInfo.setModifyDate(new Date());
                    appInfo.setLogoPicPath(logoPicPath);
                    appInfo.setLogoLocPath(localPicPath);
                    if (appInfoService.updateAppInfo(appInfo)>0){
                        return "redirect: list ";
                    }
                }else {
                    //得到当前图片在服务器的地址；

                }
            }
        }
        return "redirect: appinfomodify";
    }
    //查询版本信息
    @RequestMapping("/appversionadd")
    public String getVersionAdd(@RequestParam(value = "id",required = false) String id,Model model){
        if (id!=null&&!"".equals(id)){
            List<AppVersion> byAppId = appVersionService.getByAppId(Integer.parseInt(id));
            model.addAttribute("appVersionList",byAppId);
            model.addAttribute("appId",id);
        }
        return "developer/appversionadd";
    }
    //增加版本信息
    @RequestMapping("/addversionsave")
    public String addVersionAdd(HttpServletRequest request,HttpSession session,AppVersion appVersion,@RequestParam(value = "a_downloadLink",required = false) MultipartFile attach){
        boolean flag=false;
        String path=request.getSession().getServletContext().getRealPath("statics/uploadfiles/");
        if (!attach.isEmpty()){
            String filename = attach.getOriginalFilename();
            String extension = FilenameUtils.getExtension(filename);
            //定义文件大小
            int size=500000;
            if (filename.length()>size){
                request.setAttribute("error","文件不合法");
            }else if (!extension.equalsIgnoreCase("sql")){
                request.setAttribute("error","文件不合法");
            }else {
                //绝对路径
                String apkLocaPath=path+"/"+appVersion.getAppName()+"."+extension;
                File file=new File(apkLocaPath);
                if (!file.exists()){
                    file.mkdir();
                }
                try {
                    attach.transferTo(file);
                    flag=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (flag){
                    appVersion.setApkLocPath(apkLocaPath);
                    appVersion.setCreatedBy(1);
                    appVersion.setCreationDate(new Date());
                    Integer integer = appVersionService.addAppVersion(appVersion);
                    Integer appid=appVersion.getAppId();
                    AppInfo appInfo=new AppInfo();
                    appInfo.setId(appid);
                    appInfo.setVersionId(appVersion.getId());
                    Integer integer1 = appInfoService.updateAppInfo(appInfo);
                    if (integer1>0){
                        return "redirect:list";
                    }
                }
            }
        }

        return "redirect:appversionadd?id="+appVersion.getAppId();
    }
    @RequestMapping("/appversionmodify")
    public String getUpdateAppVersion(Model model,@RequestParam(value = "aid",required = false) String aid,@RequestParam(value = "vid") String vid){
        if (aid!=null&&!"".equals(aid)){
            List<AppVersion> byAppId = appVersionService.getByAppId(Integer.parseInt(aid));
            model.addAttribute("appVersionList",byAppId);
        }
        if (vid!=null&&!"".equals(vid)){
            AppVersion appVersionByVid = appVersionService.getAppVersionByVid(Integer.parseInt(vid));
            model.addAttribute("appVersion",appVersionByVid);
        }
        return "developer/appversionmodify";
    }
    @RequestMapping("/delapp.json")
    @ResponseBody
    public Map<String,Object> delApp(@RequestParam(value = "id",required = false) String id){
        Map<String,Object> map=new HashMap<>();
        try {
            appInfoService.delApp(id);
        } catch (Exception e) {
            e.printStackTrace();
           map.put("delResult","false");
        }
        return map;
    }

}
