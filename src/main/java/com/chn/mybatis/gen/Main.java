package com.chn.mybatis.gen;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import com.chn.mybatis.gen.trans.TableTrans;
import com.chn.mybatis.gen.utils.DBUtils;

public class Main {

	private static final String EOL = System.getProperty("line.separator");
	public static final String ROOT_FILE_PATH = Main.class.getResource("/").getPath().replace("%20", " ");//读取文件的路径
	public static final String GEN_FILE_PATH = "/F:/workspace-new/tontisa-business/";//生成文件的路径
	public static final String PACKAGE_PATH = ROOT_FILE_PATH + "com/chn/mybatis/gen/tpl";
	public static final File GEN_FOLDER = new File(GEN_FILE_PATH);
	public static final String GEN_FOLDER_MAPPER_XML_PATH = "/F:/workspace-new/tontisa-business/";
	public static final File GEN_FOLDER_MAPPER_XML = new File(GEN_FOLDER_MAPPER_XML_PATH);
	public static final String GEN_PACKAGE = "com.tontisa.business";
	public static final GroupTemplate group = new GroupTemplate(new File(PACKAGE_PATH));
	
	public static void main(String[] args) throws Exception {
		System.out.println(ROOT_FILE_PATH);
		group.setCharset("UTF-8");
		Connection conn = DBUtils.getConn();
		DatabaseMetaData dbmd = DBUtils.getDatabaseMetaData(conn);
		String dbType = dbmd.getDatabaseProductName();
		DBUtils.loadMetadata(dbmd);
		
//		for (String tableName : TableMetadata.getAllTables().keySet()) {
		//生成单表测试
		List<String> tableNameList = new ArrayList<>();
		tableNameList.add("Line_Plan_Cost_QianZheng");
		for (String tableName : tableNameList) {
//			generateXml(tableName, dbType);
			generateEntity(tableName, dbType);
//			generateService(tableName, dbType);
//			generateMapper(tableName, dbType);
//			generateServiceImpl(tableName, dbType);
//			generateDao(tableName, dbType);
			
			
//			generateWeb(tableName, dbType);
//			generateDatatableHtml(tableName, dbType);
//			generateDialogInfoHtml(tableName, dbType);
//			generateDialogUpdateHtml(tableName, dbType);
		}
	}

	private static void generateDialogInfoHtml(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate(dbType + "-dialog-info-html.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		template.set("title", "tableName管理");
		template.set("startTag_", "${");
		template.set("endTag_", "}");
		writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-info.jsp");
		FileUtils.write(new File(GEN_FOLDER, "/list/" + trans.getLowerStartClassName() + "-info.jsp"), template.getTextAsString(), "UTF-8", true);
	}

	private static void generateDialogUpdateHtml(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate(dbType + "-dialog-update-html.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		template.set("title", "tableName管理");
		template.set("startTag_", "${");
		template.set("endTag_", "}");
		writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-update.jsp");
		FileUtils.write(new File(GEN_FOLDER, "/list/" + trans.getLowerStartClassName() + "-update.jsp"), template.getTextAsString(), "UTF-8", true);
	}

	private static void writeTag(TableTrans trans, String basePath) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"	pageEncoding=\"UTF-8\"%>").append(EOL);
		buffer.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append(EOL);
		buffer.append("<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\"%>").append(EOL);
		buffer.append("<c:set var=\"ctx\" value=\"${pageContext.request.contextPath}\" />").append(EOL);
		FileUtils.write(new File(GEN_FOLDER, basePath), buffer.toString());
	}

	private static void generateDatatableHtml(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate(dbType + "-datatable-html.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		template.set("startTag_", "${");
		template.set("endTag_", "}");
		template.set("title", "tableName管理");
		template.set("addEntity", "添加");
		template.set("startTime", "开始时间");
		template.set("endTime", "结束时间");
		template.set("searchText", "searchText");
		template.set("searchButton", "searchButton");
		template.set("export", "导出");
		template.set("name", "操作");
		template.set("infoName", "详细");
		template.set("deleteName", "删除");
		template.set("updateName", "更新");

		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-list.jsp");
		FileUtils.write(new File(GEN_FOLDER, "/list/" + trans.getLowerStartClassName() + "-list.jsp"), template.getTextAsString(), "UTF-8", true);
	}

	private static void generateXml(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("mapper-xml.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER_MAPPER_XML, "tontisa-business-dao/src/main/resources/com/tontisa/business/dao/impl/mssql/" + trans.getUpperStartClassName() + "Mapper.xml"), template.getTextAsString(), "UTF-8");
	}

	private static void generateDao(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("dao-java.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER, "/dao/" + trans.getUpperStartClassName() + "Dao.java"), template.getTextAsString(), "UTF-8");
	}

	private static void generateWeb(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("web-java.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER, "/controller/" + trans.getUpperStartClassName() + "Controller.java"), template.getTextAsString(),
				"UTF-8");
	}

	private static void generateService(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("service-java.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER, "tontisa-business-service/src/main/java/com/tontisa/business/service/" + trans.getUpperStartClassName() + "Service.java"), template.getTextAsString(), "UTF-8");
	}

	private static void generateServiceImpl(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("serviceImpl-java.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER, "tontisa-business-service-impl/src/main/java/com/tontisa/business/service/impl/" + trans.getUpperStartClassName() + "ServiceImpl.java"), template.getTextAsString(), "UTF-8");
	}
	
	private static void generateMapper(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("mapper-java.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		FileUtils.write(new File(GEN_FOLDER, "tontisa-business-service-impl/src/main/java/com/tontisa/business/mapper/" + trans.getUpperStartClassName() + "Mapper.java"), template.getTextAsString(), "UTF-8");
	}
	
	private static void generateEntity(String tableName, String dbType) throws Exception {
		Template template = group.getFileTemplate("bean.txt");
		if (template == null)
			throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
		TableTrans trans = TableTrans.find(tableName);
		template.set("package", GEN_PACKAGE);
		template.set("table", trans);
		System.out.println(template.getTextAsString());
		FileUtils.write(new File(GEN_FOLDER, "tontisa-business-entity/src/main/java/com/tontisa/business/entity/" + trans.getUpperStartClassName() + ".java"), template.getTextAsString());
	}
}
