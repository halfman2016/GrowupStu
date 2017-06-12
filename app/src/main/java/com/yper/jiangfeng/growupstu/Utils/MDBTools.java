package com.yper.jiangfeng.growupstu.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.yper.jiangfeng.growupstu.Module.Annouce;
import com.yper.jiangfeng.growupstu.Module.Comment;
import com.yper.jiangfeng.growupstu.Module.DayCheckListAction;
import com.yper.jiangfeng.growupstu.Module.DayCheckRec;
import com.yper.jiangfeng.growupstu.Module.DayCommonAction;
import com.yper.jiangfeng.growupstu.Module.GradeClass;
import com.yper.jiangfeng.growupstu.Module.Photopic;
import com.yper.jiangfeng.growupstu.Module.PicPinAction;
import com.yper.jiangfeng.growupstu.Module.PinAction;
import com.yper.jiangfeng.growupstu.Module.Rank;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.Module.Teacher;
import com.yper.jiangfeng.growupstu.Module.Updateobj;
import com.yper.jiangfeng.growupstu.Module.Zan;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Feng on 2016/7/17.
 */
public class MDBTools {

    //    private static   MongoCredential credential;
//    private static    MongoClient mongoClient;
    // private static final MongoClient mongoClient = new MongoClient("192.168.31.188", 27017);
    private    MongoCredential credential = MongoCredential.createScramSha1Credential("halfman","lizhi","halfman21".toCharArray());
    private    MongoClient mongoClient = new MongoClient(new ServerAddress("boteteam.com", 27017),Arrays.asList(credential));
    // private static MongoClientURI uri = new MongoClientURI("mongodb://halfman:halfman21@boteteam.com/?authSource=db1&authMechanism=SCRAM-SHA-1");
// private static    MongoClient mongoClient = new MongoClient(uri);
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection ;
    public MDBTools() {
//       credential = MongoCredential.createScramSha1Credential("halfman","lizhi","halfman21".toCharArray());
//       mongoClient = new MongoClient(new ServerAddress("boteteam.com", 27017),Arrays.asList(credential));
        mongoDatabase = mongoClient.getDatabase("lizhi");
    }

    public Student stuLogin(String name, String pwd){

        mongoCollection=mongoDatabase.getCollection("students");

        List<BasicDBObject> objects = new ArrayList<BasicDBObject>();
        objects.add(new BasicDBObject("name", name));
        objects.add(new BasicDBObject("pwd",pwd));

        BasicDBObject query=new BasicDBObject();

        query.put("$and",objects);

        MongoCursor cursor = mongoCollection.find(query).iterator();


        while (cursor.hasNext()) {

            Document doc= (Document) cursor.next();
            Gson gson=new GsonBuilder().create();

            return gson.fromJson(doc.toJson(),Student.class);

        }

        return null;
    }

    public boolean teacherLogin(String Tid,String pwd){

        mongoCollection=mongoDatabase.getCollection("teachers");

        List<BasicDBObject> objects = new ArrayList<BasicDBObject>();
        objects.add(new BasicDBObject("Tid", Tid));
        objects.add(new BasicDBObject("pwd",pwd));

        BasicDBObject query=new BasicDBObject();

        query.put("$and",objects);

        MongoCursor cursor = mongoCollection.find(query).iterator();


        while (cursor.hasNext()) {

            return true;

        }

        return false;
    }

    public Teacher getTeacher(String Tid){

        Teacher teacher=null;
        mongoCollection =mongoDatabase.getCollection("teachers");
        BasicDBObject basicDBObject=new BasicDBObject("Tid",Tid);
        MongoCursor cursor=mongoCollection.find(basicDBObject).iterator();

        while (cursor.hasNext())
        {


            Document doc=(Document) cursor.next();

            Gson gson=new GsonBuilder().create();
            teacher=gson.fromJson(doc.toJson(),Teacher.class);

        }

        return teacher;
    }



    public Subject getSubject(String _id){
        Subject subject=null;
        mongoCollection=mongoDatabase.getCollection("subjects");
        BasicDBObject basicDBObject=new BasicDBObject("_id",_id);
        MongoCursor cursor=mongoCollection.find(basicDBObject).iterator();
        while (cursor.hasNext())
        {
            Document doc=(Document) cursor.next();
            Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
            subject=gson.fromJson(doc.toJson(),Subject.class);
        }
        return subject;
    }
    public void saveSubject(Subject subject){
        mongoCollection=mongoDatabase.getCollection("subjects");
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        String json=gson.toJson(subject);
        mongoCollection.insertOne(Document.parse(json));

    }

    public  void updateSubject(Subject subject){
        mongoCollection=mongoDatabase.getCollection("subjects");
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        String json=gson.toJson(subject);
        mongoCollection.findOneAndReplace(Filters.eq("_id",subject.get_id().toString()),Document.parse(json));

    }



    public void addStu(Student student) {
        mongoCollection = mongoDatabase.getCollection("students");
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(student);
        mongoCollection.insertOne(Document.parse(json));

    }

    public void addTea(Teacher tea)

    {
        mongoCollection = mongoDatabase.getCollection("teachers");
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(tea);
        mongoCollection.insertOne(Document.parse(json));

    }
    public ArrayList<Student> getStus()
    {
        ArrayList<Student> stus=new ArrayList<>();
        Student stu;
        mongoCollection=mongoDatabase.getCollection("students");
        FindIterable<Document> iterator=mongoCollection.find();
        MongoCursor mongoCursor=iterator.iterator();
        Gson gson=new GsonBuilder().create();
        while (mongoCursor.hasNext())
        {
            String json;
            Document doc=(Document) mongoCursor.next();
            json=doc.toJson();
            stu=gson.fromJson(json,Student.class);
            stus.add(stu);
        }
        return stus;
    }

    public  ArrayList<Subject> getSubjects(){
        ArrayList<Subject> subjects=new ArrayList<>();
        Subject subject;
        mongoCollection=mongoDatabase.getCollection("subjects");
        FindIterable<Document> iterable=mongoCollection.find().sort(new BasicDBObject("endTime",-1));
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();

            subject=gson.fromJson(doc.toJson(),Subject.class);
            subjects.add(subject);

        }
        return  subjects;
    }

    public ArrayList<Teacher> getTeas()
    {
        ArrayList<Teacher> teas = new ArrayList<>();
        Teacher tea;
        mongoCollection = mongoDatabase.getCollection("teachers");
        FindIterable<Document> iterable=mongoCollection.find();
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();
        while (mongoCursor.hasNext()){
            Document doc= (Document) mongoCursor.next();
            String json=doc.toJson();
            tea = gson.fromJson(json, Teacher.class);
            teas.add(tea);
        }
        return teas;
    }


    public ArrayList<GradeClass> getGradeClasses() {
        ArrayList<GradeClass> gradeClasses = new ArrayList<>();
        GradeClass gradeClass ;

        mongoCollection=mongoDatabase.getCollection("classes");
        FindIterable<Document> iterable=mongoCollection.find();
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();
            String json=doc.toJson();
            gradeClass=gson.fromJson(json,GradeClass.class);
            gradeClasses.add(gradeClass);

        }


        return gradeClasses;
    }

    public GradeClass getGradeClass(UUID uuid){
        GradeClass gradeClass;
        mongoCollection=mongoDatabase.getCollection("classes");
        Document document=mongoCollection.find(Filters.eq("_id",uuid.toString())).first();
        Gson gson=new GsonBuilder().create();

        gradeClass=gson.fromJson(document.toJson(),GradeClass.class);

        return gradeClass;
    }

    public List<PinAction> getPinActions(){
        List<PinAction> pinActions=new ArrayList<>();
        PinAction pinAction;
        mongoCollection=mongoDatabase.getCollection("pinactions");
        FindIterable<Document> iterable=mongoCollection.find();
        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        while (mongoCursor.hasNext())
        {
            Document doc=(Document)mongoCursor.next();
            pinAction=gson.fromJson(doc.toJson(),PinAction.class);
            pinActions.add(pinAction);
        }

        return pinActions;
    }

    public void saveGradeClasses(ArrayList<GradeClass> gradeClasses){
        mongoCollection=mongoDatabase.getCollection("classes");
        mongoCollection.deleteMany(Filters.exists("_id"));

        Gson gson=new GsonBuilder().create();
        Document doc = new Document();
        List<Document> docs=new ArrayList<>();
        for (GradeClass temp:gradeClasses
                ) {
            doc=Document.parse(gson.toJson(temp));
            docs.add(doc);

        }

        mongoCollection.insertMany(docs);

    }


    public void savePicPinAcions(List<PicPinAction> picPinActions,Photopic photopic){

        mongoCollection=mongoDatabase.getCollection("picpinactions");

        mongoCollection.deleteMany(Filters.eq("picsrcid",photopic.get_id().toString()));


        if (picPinActions.size()>0) {
            Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
            Document doc = new Document();
            List<Document> docs = new ArrayList<>();

            for (PicPinAction pic : picPinActions) {
                doc = Document.parse(gson.toJson(pic));
                docs.add(doc);
            }
            mongoCollection.insertMany(docs);
        }
    }

    public  void saveGradeClass(GradeClass gc){

        mongoCollection = mongoDatabase.getCollection("classes");

        Gson gson=new GsonBuilder().create();
        String json = gson.toJson(gc);
        mongoCollection.insertOne(Document.parse(json));
    }

    public void updateGradeClass(GradeClass gc){
        mongoCollection =mongoDatabase.getCollection("classes");
        Gson gson=new GsonBuilder().create();
        Document document=Document.parse(gson.toJson(gc));
        String temp=gc.get_id().toString();
        Document update=new Document();
        update.put("$set",document);
        //重点学习的重点
        mongoCollection.updateOne(Filters.eq("_id",temp),update);

    }

    public HashMap<String,Rank> getRanks(){

        HashMap<String,Rank> ranks = new HashMap();

        mongoCollection= mongoDatabase.getCollection("configs");



        FindIterable<Document> iterator=mongoCollection.find(Filters.exists("ranks"));

        MongoCursor mongoCursor=iterator.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();
            String json= (String) doc.get("ranks");

            Type type =new TypeToken<HashMap<String,Rank>>(){}.getType();
            ranks=gson.fromJson(json,type);
        }
        //   Log.d("myapp",books.toString());
        return ranks;
    }

    public void saveRanks(Map<String,Rank> ranks){

        mongoCollection=mongoDatabase.getCollection("configs");
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(ranks);

        Document doc=new Document().append("ranks",json);

        Document filer=Document.parse("{ranks:{$exists:true}}");
        mongoCollection.findOneAndReplace(filer,doc);

    }

    public void saveDefaultValuestoDataBase(Map<String,Integer> map)
    {
        mongoCollection=mongoDatabase.getCollection("configs");
        Document doc=new Document("defaultValues",map);
        Document filer=Document.parse("{defaultValues:{$exists:true}}");
        mongoCollection.deleteMany(filer);
        mongoCollection.insertOne(doc);


    }

    public Map<String, Integer>  getDefaultValues(){
        Map<String, Integer> map = new HashMap<>();
        mongoCollection=mongoDatabase.getCollection("configs");
        Bson doc=  Filters.exists("defaultValues");
        Gson gson=new GsonBuilder().create();
        FindIterable<Document> docs=mongoCollection.find(doc);
        MongoCursor cur=docs.iterator();
        while (cur.hasNext())
        {

            Document document= (Document) cur.next();
            map= (Map<String, Integer>) document.get("defaultValues");
//            Type type =new TypeToken<HashMap<String,Integer>>(){}.getType();
//            map=gson.fromJson(json,type);
        }

        return map;


    }


    public boolean addPhoto(Photopic photopic) {
        mongoCollection=mongoDatabase.getCollection("photos");
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();

        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmssSSS").format(new Date());
        UploadManager uploadManager=new UploadManager();
        String token=Utils.getToken();
        try {
            uploadManager.put(photopic.getPhotopreview(),timeStamp,token);
            photopic.setPhotopreview(null);
            photopic.setPhotofile(null);
            photopic.setPicname(timeStamp);
            mongoCollection.insertOne(Document.parse(gson.toJson(photopic)));

        } catch (QiniuException e) {
            e.printStackTrace();
            return  false;
        }


        return true;

    }

    public boolean chgTeaPwd(Teacher teacher ,String Newpwd){
        mongoCollection=mongoDatabase.getCollection("teachers");
        Document filer=Document.parse("{_id:\""+teacher.get_id().toString()+"\"}");
        Document update=Document.parse("{$set:{\"pwd\":\""+Newpwd+"\"}}");

        mongoCollection.findOneAndUpdate(filer,update);


        return true;
    }

    public boolean delPhotopic(Photopic photopic)
    {
        mongoCollection=mongoDatabase.getCollection("photos");

        Document filer=Document.parse("{_id:\""+photopic.get_id().toString()+"\"}");
        DeleteResult result= mongoCollection.deleteOne(Filters.eq("_id",photopic.get_id().toString()));
        if(result.getDeletedCount()==1)
        {
            return true;
        }
        else
        {return false;}




        //暂时不删除服务器资源
    }

    public List<PicPinAction> getPicpinactions(Photopic photopic)
    {
        List<PicPinAction> picPinActions=new ArrayList<>();
        PicPinAction picPinAction;
        mongoCollection=mongoDatabase.getCollection("picpinactions");
        MongoCursor mongoCursor=mongoCollection.find(Filters.eq("picsrcid",photopic.get_id().toString())).iterator();
        while (mongoCursor.hasNext()){
            Document doc=(Document)mongoCursor.next();
            Gson gson=new GsonBuilder().create();
            picPinAction=gson.fromJson(doc.toJson(),PicPinAction.class);
            picPinActions.add(picPinAction);
        }

        return  picPinActions;
    }


    public Photopic  getPhoto(UUID photoid){
        mongoCollection=mongoDatabase.getCollection("photos");
        Photopic photopic=null;
        Document filer=new Document();
        filer.put("_id",photoid.toString());  //查询必须是 string 类型,否则就是找不到哦

        MongoCursor cursor =mongoCollection.find(filer).iterator();
        while (cursor.hasNext()) {

            Document doc= (Document) cursor.next();
            Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
            photopic=gson.fromJson(doc.toJson(),Photopic.class);
        }
        return photopic;
    }

    public List<Photopic>  getfreePhotopic(){
        List<Photopic> photopics=new ArrayList<>();
        Photopic photopic;
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        mongoCollection=mongoDatabase.getCollection("photos");

        MongoCursor cursor=mongoCollection.find(Filters.exists("belongToSubject",false)).sort(new BasicDBObject("picname",-1)).iterator();
        while (cursor.hasNext()){
            Document doc=(Document)cursor.next();
            photopic=gson.fromJson(doc.toJson(),Photopic.class);
            photopics.add(photopic);
        }
        return photopics;
    }

    public List<Photopic> getSubjectPhoto(Subject subject){
        List<Photopic> photopics=new ArrayList<>();
        Photopic photopic;
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        mongoCollection=mongoDatabase.getCollection("photos");
        MongoCursor cursor=mongoCollection.find(Filters.eq("belongToSubject",subject.get_id().toString())).sort(new BasicDBObject("picname",-1)).iterator();
        while (cursor.hasNext()){
            Document doc =(Document) cursor.next();
            photopic =gson.fromJson(doc.toJson(),Photopic.class);
            photopics.add(photopic);

        }
        return photopics;

    }

    public List<Photopic> getTeacherPics(Teacher teacher) {
        List<Photopic> photopics = new ArrayList<>();
        Photopic photopic;
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        mongoCollection = mongoDatabase.getCollection("photos");
        MongoCursor cursor = mongoCollection.find(Filters.eq("photoauthorid", teacher.get_id().toString())).sort(new BasicDBObject("picname", -1)).iterator();
        while (cursor.hasNext()) {
            Document doc = (Document) cursor.next();
            photopic = gson.fromJson(doc.toJson(), Photopic.class);
            photopics.add(photopic);


        }
        return photopics;
    }




    public  List<DayCheckListAction> getDayCheckListActions(String typeName){
        List<DayCheckListAction> dayCheckListActions=new ArrayList<>();
        DayCheckListAction dayCheckListAction;
        Gson gson=new GsonBuilder().create();
        mongoCollection=mongoDatabase.getCollection("daychecklistactions");
        MongoCursor cursor=mongoCollection.find(Filters.eq("actionType",typeName)).iterator();
        while (cursor.hasNext())
        {
            Document doc=(Document) cursor.next();
            dayCheckListAction=gson.fromJson(doc.toJson(),DayCheckListAction.class);
            dayCheckListActions.add(dayCheckListAction);
        }
        return  dayCheckListActions;
    }

    public List getDayCheckListType(){
        DB db=mongoClient.getDB("lizhi");
        DBCollection collection=db.getCollection("daychecklistactions");
        List cl1=collection.distinct("actionType");

        return cl1;
    }

    public void addDaycheckListAction(DayCheckListAction dayCheckListAction){
        mongoCollection=mongoDatabase.getCollection("daychecklistactions");
        Gson gson=new GsonBuilder().create();
        mongoCollection.insertOne(Document.parse(gson.toJson(dayCheckListAction)));

    }


    public List<Annouce> getAnnouces(Subject subject){
        List<Annouce> annouces=new ArrayList<>();
        mongoCollection=mongoDatabase.getCollection("annouces");
        Annouce ann;
        Gson gson =new GsonBuilder().create();
        MongoCursor cursor=mongoCollection.find(Filters.eq("belongtoSubject",subject.get_id().toString())).sort(new BasicDBObject("AnnouceTime",-1)).iterator();
        while (cursor.hasNext())
        {
            Document doc=(Document)cursor.next();
            ann=gson.fromJson(doc.toJson(),Annouce.class);
            annouces.add(ann);
        }

        return  annouces;
    }

    public Annouce getAnnouceLatest(Subject subject)
    {
        Annouce annouce=null;
        mongoCollection=mongoDatabase.getCollection("annouces");
        Document doc=mongoCollection.find(Filters.eq("belongtoSubject",subject.get_id().toString())).sort(new BasicDBObject("AnnouceTime",-1)).first();
        if(doc==null)
        {
            annouce=new Annouce("","",new Date());
            return annouce;
        }
        else
        {
            Gson gson=new GsonBuilder().create();
            annouce=gson.fromJson(doc.toJson(),Annouce.class);

            return  annouce;
        }   }

    public void addAnnouce(Subject subject,Annouce ann){
        mongoCollection=mongoDatabase.getCollection("annouces");
        Annouce annouce=ann;
        Gson gson=new GsonBuilder().create();
        ann.setBelongtoSubject(subject.get_id());
        mongoCollection.insertOne(Document.parse(gson.toJson(ann)));
    }
    public static File putFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public void addDayCommonActionRecs(List<DayCommonAction> daylist,GradeClass gradeClass,Teacher teacher,String typename,Date date){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String strdate=sdf.format(date);

        DayCheckRec dayCheckRec=new DayCheckRec(daylist,gradeClass,typename,strdate);
        dayCheckRec.setCheckedteachername(teacher.getName());
        dayCheckRec.setCheckedteacherid(teacher.get_id());
        mongoCollection=mongoDatabase.getCollection("daycommonactionRecs");

        Gson gson=new GsonBuilder().create();
        Document doc = new Document();
        doc=Document.parse(gson.toJson(dayCheckRec));
        mongoCollection.insertOne(doc);

    }

    public DayCheckRec getDayCheckRec(Date date,GradeClass gradeClass,String typename){
        DayCheckRec dayCheckRec;

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String strdate=sdf.format(date);
        mongoCollection =mongoDatabase.getCollection("daycommonactionRecs");

        MongoCursor cursor= mongoCollection.find(Filters.eq("strdate",strdate)).iterator();

        Gson gson=new GsonBuilder().create();

        while (cursor.hasNext())
        {
            Document doc= (Document)cursor.next();
            dayCheckRec=gson.fromJson(doc.toJson(),DayCheckRec.class);

            if((dayCheckRec.getGradeClass().getName().equals(gradeClass.getName())) && (dayCheckRec.getTypename().equals(typename)) ) {

                return dayCheckRec;
            }
        }

        dayCheckRec=new DayCheckRec();
        return dayCheckRec;
    }

    public  ArrayList<DayCommonAction> getTypedDayActions(String typeName){

        ArrayList <DayCommonAction> result=new ArrayList<>();


        DayCommonAction dayca;

        mongoCollection=mongoDatabase.getCollection("daycommonactions");
        FindIterable<Document> iterable;
        if (typeName=="")
        { iterable=mongoCollection.find();}
        else
        {  iterable=mongoCollection.find(Filters.eq("actionType",typeName));}

        MongoCursor mongoCursor=iterable.iterator();
        Gson gson=new GsonBuilder().create();

        while (mongoCursor.hasNext())
        {
            Document doc=(Document) mongoCursor.next();
            String json=doc.toJson();
            dayca=gson.fromJson(json,DayCommonAction.class);
            result.add(dayca);

        }

        return result;
    }

    public void addPinAction(PinAction pinAction)
    {
        mongoCollection=mongoDatabase.getCollection("pinactions");
        Gson gson=new GsonBuilder().create();
        Document doc=Document.parse(gson.toJson(pinAction));
        mongoCollection.insertOne(doc);
    }


    public Updateobj getUpdate(String apkname){

        Updateobj updateobj;
        mongoCollection=mongoDatabase.getCollection("update");
        Document doc=mongoCollection.find(Filters.eq("apkname",apkname)).first();
        Gson gson=new GsonBuilder().create();
        updateobj= gson.fromJson(doc.toJson(),Updateobj.class);
        return updateobj;
    }

    public  void savePhotopic(Photopic photopic){
        mongoCollection=mongoDatabase.getCollection("photos");
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();

        Document doc=Document.parse(gson.toJson(photopic));
        mongoCollection.updateOne(Filters.eq("_id",photopic.get_id().toString()),new Document("$set",doc));
    }


    public  List<Photopic> getToppics(){
        List<Photopic> photopics=new ArrayList<>();
        mongoCollection=mongoDatabase.getCollection("photos");
        MongoCursor mongoCursor=mongoCollection.find().sort(new BasicDBObject("picname",-1)).limit(30).iterator();
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();

        while (mongoCursor.hasNext())
        {
            Document doc= (Document) mongoCursor.next();
            photopics.add(gson.fromJson(doc.toJson(),Photopic.class));
        }
        return photopics;
    }

    public  List<DayCheckRec> getTopDaychecks(){
        List<DayCheckRec> dayCheckRecs=new ArrayList<>();
        mongoCollection=mongoDatabase.getCollection("daycommonactionRecs");
        MongoCursor mongoCursor=mongoCollection.find().sort(new BasicDBObject("strdate",1)).limit(5).iterator();
        Gson gson=new GsonBuilder().setDateFormat("MMM d, yyyy h:mm:ss a").create();
        while (mongoCursor.hasNext())
        {
            Document doc= (Document) mongoCursor.next();
            DayCheckRec dy;
            String json=doc.toJson();
            dy=gson.fromJson(doc.toJson(),DayCheckRec.class);
            // dayCheckRecs.add(gson.fromJson(doc.toJson(),DayCheckRec.class));

            dayCheckRecs.add(dy);
        }
        return dayCheckRecs;
    }

}
