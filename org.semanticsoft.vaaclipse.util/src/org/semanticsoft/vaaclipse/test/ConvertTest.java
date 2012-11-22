///**
// * 
// */
//package org.semanticsoft.vaaclipse.test;
//
//import org.semanticsoft.vaaclipse.util.Utils;
//
///**
// * @author rushan
// *
// */
//public class ConvertTest
//{
//	final static String uri1 = "platform:/plugin/org.somebundle/img/blabla.png";
//	
//	static final String url1 = "/VAADIN/themes/generated_theme/org.somebundle/img/blabla.png";
//	static final String url2 = "/VAADIN/themes/reindeer/img/blabla.png";
//	
//	public static void main(String[] args)
//	{
//		System.out.println(Utils.restorePath("/VAADIN/themes/reindeer/styles.css", "generated_theme", "path to css"));
//		System.out.println(Utils.restorePath("/VAADIN/themes/generated_theme/styles.css", "generated_theme", "path to css"));
//		System.out.println(Utils.restorePath(url1, "generated_theme", "path to css"));
//		System.out.println(Utils.restorePath(url2, "generated_theme", "path to css"));
//		
//		
//		System.out.println(Utils.convertPath(uri1));
//		
//		System.out.println(Utils.restorePath("/VAADIN/themes/generated_theme/" + Utils.convertPath(uri1), "generated_theme", "path to css").equals(uri1));
//	}
//}
