package com.nbcedu.bas.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
/**
 * <p>标题： </p>
 * <p>功能： </p>

 * @version 1.0
 */
public class StrUtil {
	protected StrUtil() {
	}

	/**
	 * 指定一个字符及字符的个数，生成一个字符串 示例：StrUtil.newString('a',10); 结果：aaaaaaaaaa
	 * 
	 * @param c
	 *            指定的字符
	 * @param count
	 *            字符出现的次数
	 * @return 新字符串
	 */
	public static final String newString(char c, int count) {
		StringBuilder strBuffer = new StringBuilder();
		for (; count > 0; count--)
			strBuffer.append(c);
		return strBuffer.toString();// new String(strBuffer);
	}

	/**
	 * 将整数x转换为指定长度的十进制的字符串，如何长度不足用0补齐 示例1：StrUtil.toString(125, 5); 结果：00125
	 * 
	 * @param x
	 *            要进行转换的整数
	 * @param len
	 *            想要转换的最终长度
	 * @return 返回转换完成后的字符串
	 */
	public static final String toString(int x, int len) {
		// StrUtil.get
		return prefixZPadding(Integer.toString(x), len);
	}

	/**
	 * 将整数x转换为指定长度的十六进制的字符串，如何长度不足用0补齐 示例1：StrUtil.toHexString(125, 5); 结果：0007d
	 * 
	 * @param x
	 *            要进行转换的整数
	 * @param len
	 *            想要转换的最终长度
	 * @return 返回转换完成后的字符串
	 */
	public static final String toHexString(int x, int len) {
		// StrUtil.get
		return prefixZPadding(Integer.toHexString(x), len);
	}

	static private String prefixZPadding(String s, int len) {
		if (s.length() < len) {
			int count = len - s.length();
			final StringBuilder strBuffer = new StringBuilder();
			for (; count > 0; count--)
				strBuffer.append('0');
			strBuffer.append(s);
			return strBuffer.toString();
		}
		return s;
	}

	/**
	 * 宏替换. 宏名必须为一个标准的Java标识符,对于带参数的宏,宏名后面加$符再加参数个数, 例如 MNAME$3
	 * 表示一个含三个参数的宏名，宏值中使用 $0,%1,... 表示被替换的参数 <blockquote>
	 * 
	 * <pre>
	 * Hashtable macro = new Hashtable();
	 * macro.put(&quot;MNAME$3&quot;, &quot;%0+%1+%2&quot;);
	 * macro.put(&quot;PROCP&quot;, &quot;p()&quot;);
	 * String text = macroReplace(&quot;MNAME$3(x,y,z)+PROCP&quot;, macro);
	 * // 结果为 &quot;x+y+z+p()&quot;
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param text
	 *            将进行宏替换的字符串
	 * @param macro
	 *            宏定义
	 * @return 替换后的结果
	 */
	public static final String replaceMacro(String text, java.util.Map macro) // 1.1
	{
		return replaceMacro(text, macro, (char) 0, (char) 0, 0);
	}

	/**
	 * 宏替换. 宏名必须为一个标准的Java标识符,对于带参数的宏,宏名后面加$符再加参数个数, 例如 MNAME$3
	 * 表示一个含三个参数的宏名，宏值中使用 $0,%1,... 表示被替换的参数. 进行宏替换的字符串中只有被指定括号括住的宏名才被替换.
	 * <blockquote>
	 * 
	 * <pre>
	 * Hashtable macro = new Hashtable();
	 * macro.put(&quot;MNAME$3&quot;, &quot;%0+%1+%2&quot;);
	 * macro.put(&quot;PROCP&quot;, &quot;p()&quot;);
	 * macro.put(&quot;MNAME(3)&quot;, &quot;%0+%1+%2&quot;);
	 * macro.put(&quot;PROCP&quot;, &quot;p()&quot;);
	 * String text = macroReplace(&quot;{MNAME$3}(x,y,z)+{PROCP}&quot;, macro, '{', '}');
	 * text = macroReplace(&quot;{MNAME}(x,y,z)+{PROCP}&quot;, macro, '{', '}');
	 * // 结果为 &quot;x+y+x+p()&quot;
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param text
	 *            将进行宏替换的字符串
	 * @param prefix
	 *            进行宏替换的字符串中括住宏名的左括号
	 * @param suffix
	 *            进行宏替换的字符串中括住宏名的右括号
	 * @param macro
	 *            宏定义
	 * @return 替换后的结果
	 */
	public static final String replaceMacro(String text, java.util.Map macro,
			char prefix, char suffix) // 1.1
	{
		return replaceMacro(text, macro, prefix, suffix, 0);
	}

	/**
	 * 
	 * @param text
	 * @param macro
	 * @param prefix
	 * @param suffix
	 * @param options
	 *            #1 -- 替换 String 中的 宏 #2 -- 宏 值的 null =》 "" #4 #8 #16 宏 值的 null
	 *            =》 "0" #32 不处理 XXX$<参数个数> 的 #64 -- 宏名中 可以含 .
	 * 
	 * @return
	 */
	public static final String replaceMacro(String text, java.util.Map macro,
			char prefix, char suffix, int options) // 1.1
	{
		if (text == null || macro == null)
			return text;
		final boolean replaceStr = (options & 1) != 0;
		int nCount = 0;
		StringBuilder strBuffer = new StringBuilder();
		char charStr = 0, charStr2 = 0; // In " or '
		int ltext = text.length();
		// int j0 = 0;
		for (int i = 0; i < ltext;) {
			char c = text.charAt(i);
			if (charStr == 0 && Character.isJavaIdentifierStart(c)) {
				char id[] = new char[64];
				id[0] = c;
				int j = 1;
				int p$ = -1;
				int nPara = 0;
				for (j = 1; j < 64 && i + j < ltext;) {
					c = text.charAt(i + j);
					if (!Character.isJavaIdentifierPart(c)
							&& (c != '.' || (options & 64) != 0))
						break;
					if (c == '$' && (options & 32) == 0)
						p$ = j;
					else if (c < '0' || c > '9')
						p$ = -1;
					else if (p$ > 0)
						nPara = nPara * 10 + (c - '0');
					id[j++] = c;
				}
				i += j;
				boolean notMacroId = suffix != 0
						&& (i >= ltext || text.charAt(i) != suffix);
				if (!notMacroId && prefix != 0) {
					int l = strBuffer.length();
					if (l == 0 || strBuffer.charAt(l - 1) != prefix)
						notMacroId = true;
				}
				String t = null;
				final String[] parmList = new String[64];
				int paramLength = -1, countParams = 0;
				if (!notMacroId) {
					String macroName = new String(id, 0, j);
					Object ot = null;
					paramLength = parseParameterList(text, suffix != 0 ? i + 1
							: i, parmList);
					if (paramLength > 0)
						for (; countParams < 64
								&& parmList[countParams] != null; countParams++)
							;
					if (countParams > 0) {
						try {
							ot = macro.get(macroName + "(" + countParams + ")");
						} catch (Throwable ex) {
						}
						if (ot != null) {
							t = ot.toString();
							nPara = countParams;
						}
					}
					if (t == null) {
						try {
							ot = macro.get(macroName);
						} catch (Throwable ex) {
						}
						t = ot == null ? null : ot.toString();
					}
					if (t == null) {
						if ((charStr2 == 0 && (options & 4) != 0)
								|| (charStr2 != 0 && (options & 9) == 9))
							return null;
						if ((options & 2) != 0)
							t = "";
						else if ((options & 16) != 0)
							t = "0";
					}
					if (t == null)
						notMacroId = true;
				}
				if (notMacroId) {
					strBuffer.append(id, 0, j);
				} else
				// id 为宏名：
				{
					if (prefix != 0) {
						// strBuffer.deleteCharAt(strBuffer.length()-1); // 1.2
						strBuffer.setLength(strBuffer.length() - 1);
					}
					if (suffix != 0)
						i++;
					nCount++;
					if (nPara > 0) {
						if (paramLength < 0)
							paramLength = parseParameterList(text, i, parmList);
						i += paramLength;
						if (parmList[nPara] != null
								|| parmList[nPara - 1] == null) {
							System.out.println("Error parameter count");
						}
						strBuffer.append(format(t, (Object[]) parmList)); // 其中
																			// %0,
																			// %1,....
					} else {
						strBuffer.append(t);
					}
				}
			} else {
				strBuffer.append(c);
				if (!replaceStr) {
					if (charStr == 0 && (c == '"' || c == '\''))
						charStr = c;
					else if (charStr == c && text.charAt(i - 1) != '\\')
						charStr = 0;
					charStr2 = charStr;
				} else {
					if (charStr2 == 0 && (c == '"' || c == '\''))
						charStr2 = c;
					else if (charStr2 == c && text.charAt(i - 1) != '\\')
						charStr2 = 0;
				}
				i++;
			}
		}
		return nCount > 0 ? strBuffer.toString() : text;
	} // macroReplace

	/*
	 * nullValue==null : 不替换
	 */
	/**
	 * @param text
	 * @param macro
	 * @param prefix
	 * @param suffix
	 * @param nullValue
	 * @param nulValueDelim
	 * @return
	 */
	public static final String macroReplace(String text, Map macro,
			String prefix, String suffix, final String nullValue,
			final char nulValueDelim) {
		if (text == null)
			return null;
		final int lprefix = prefix.length(), lsuffix = suffix.length();
		for (int p0 = 0;;) {
			final int p1 = text.indexOf(prefix, p0);
			if (p1 < 0)
				break;
			final int p2 = text.indexOf(suffix, p1 + lprefix);
			if (p2 < 0)
				break;
			String nm = text.substring(p1 + lprefix, p2);
			String nulValue = nullValue;
			if (nulValueDelim != 0) {
				int p = nm.indexOf(nulValueDelim);
				if (p >= 0) {
					nulValue = nm.substring(p + 1);
					nm = nm.substring(0, p);
				}
			}
			Object v = macro.get(nm);// ||nullValue;
			if (v == null)
				v = nulValue;
			if (v == null) {
				p0 = p2 + lsuffix;
				continue;
			}
			text = text.substring(0, p1) + v + text.substring(p2 + lsuffix);
			p0 = p1 + v.toString().length();
		}
		return text;
	}

	public static final String macroReplace(String text, Map macro, String prefix, String suffix) {
		return macroReplace(text, macro, prefix, suffix, null, (char) 0);
	}

	/**
	 * 判断字符串是否为Java有效的标识符 示例1：StrUtil.isJavaIdentifier("A12"); 结果：true
	 * 示例2：StrUtil.isJavaIdentifier("^12"); 结果：false
	 * 
	 * @param id
	 *            要接受检测的字符串
	 * @return 是否有效
	 */
	public static boolean isJavaIdentifier(String id) {
		int n = id == null ? 0 : id.length();// if(id==null || id.length()==0 )
		if (n == 0)
			return false;
		if (!Character.isJavaIdentifierStart(id.charAt(0)))
			return false;
		for (int j = 1; j < n; j++)
			if (!Character.isJavaIdentifierPart(id.charAt(j)))
				return false;
		return true;
	}

	/**
	 * 在指定字符串中查找以startWith为头(不包含该字符)直到不是java有效标识符为止,然后在以startWith为头开始查找
	 * 示例1：Arrays.toString(StrUtil.extractMacroNames("abc^def^aaa^baa", 'a'));
	 * 结果：[bc, aa, a]
	 * 
	 * @param text
	 *            要查找的原字符串
	 * @param startWith
	 *            起始字符
	 * @return 返回所有有效的标识符
	 */
	public static String[] extractMacroNames(String text, char startWith) {
		if (text == null)
			return null;
		final java.util.List<String> vm = new ArrayList<String>();
		final int n = text.length();
		for (int i = 0; i < n - 1; i++) {
			if (text.charAt(i) == startWith
					&& Character.isJavaIdentifierStart(text.charAt(i + 1))) {
				int i0 = i + 1;
				for (; i < n - 1
						&& Character.isJavaIdentifierPart(text.charAt(i + 1));) {
					i++;
				}
				vm.add(text.substring(i0, i + 1));
			}
		}
		if (vm.size() == 0)
			return null;
		return vm.toArray(new String[vm.size()]);
	}

	/**
	 * 统计一个字符串中给定范围内前缀空格的个数 示例：StrUtil.countStartSpace("    abc", 0,
	 * "    abc".length()); 结果：4 注意：为防止出现空指针异常，iEnd参数最大值为字符串的长度
	 * 
	 * @param text
	 *            要统计的字符串
	 * @param iStart
	 *            开始指针下标
	 * @param iEnd
	 *            结束指针下标
	 * @return 返回前缀空格的个数
	 */
	public static final int countStartSpace(String text, int iStart, int iEnd) {
		int n = 0;
		for (; iStart + n < iEnd; n++) {
			if (!Character.isSpaceChar(text.charAt(iStart + n)))
				break;
		}
		return n;
	}

	/**
	 * 查询字符在字符串中出现的次数 示例：StrUtil.countChar("abcabcabc", 'a'); 结果：3
	 * 
	 * @param text
	 *            要查询的字符串
	 * @param c
	 *            要查询的字符
	 * @return 出现的次数
	 */
	public static final int countChar(String text, char c) {
		if (text == null)
			return 0;
		int n = 0;
		int ltext = text.length();
		for (int i = 0; i < ltext; i++)
			if (text.charAt(i) == c)
				n++;
		return n;
	}

	/**
	 * 格式化一个字符串. 被格式化的字符串中 %0,%1,... 作为参数将被替换. 示例1： String parm[] =
	 * {"AAA","BBB","CCC"}; String text =
	 * format("xu%0+xv%1+%2",parm);结果：xuAAA+xvBBB+CCC
	 * 示例2：format("2012%08%11%2","年","月","日");结果：2012年8月1日
	 * format("2012%0012%1%2","年","月","日");结果：2012年8月1日
	 * 
	 * @param fmt
	 *            格式
	 * @param parm
	 *            参数表, fmt中的%0被parm[0]替换,%1被parm[1]替换
	 * @return 格式化后的字符串
	 */
	public static final String format(String fmt, Object... parm) {
		if (fmt == null)
			return null;
		StringBuilder r = new StringBuilder();
		final int l = fmt.length();
		for (int i = 0; i < l;) {
			int p = fmt.indexOf('%', i);
			if (p < i || p >= l - 1) {
				r.append(fmt.substring(i));
				break;
			}
			// p>=i && p<l-1
			r.append(fmt.substring(i, p));
			char c = fmt.charAt(p + 1);
			if (c >= '0' && c <= '9') {
				int j = c - '0';
				char c2;
				int p2 = p + 2;
				for (; p2 < l && (c2 = fmt.charAt(p2)) >= '0' && c2 <= '9'; p2++)
					j = j * 10 + (c2 - '0');
				if (j < parm.length && parm[j] != null)
					r.append(parm[j].toString());
				i = p2;// + 2;
			} else if (c == '%') {
				r.append('%');
				i = p + 2;
			} else {
				r.append('%');
				i = p + 1;
			}
		}
		return r.toString();
	}

	/**
	 * 参数表分析. 例如 <blockquote>
	 * 
	 * <pre>
	 * String list[] = new String[64];
	 * parseParameterList("M(XXX1,XXX2,XXX3)",1,list)
	 * // 返回结果为3 , list[0] = "XXX1", list[1] = "XXX2", list[2] = "XXX3"
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param text
	 *            含参数表的字符串
	 * @param iStart
	 *            参数表在text中的起始位置(该位置除空格外的第一个必须为'('),
	 * @param list
	 *            参数分析出的结果放在其中(必须保证list元素个数足够大)
	 * @return 参数在text中的长度
	 */
	public static final int parseParameterList(String text, int iStart,
			String[] list) {
		return parseParameterList(text, iStart, list, null, null);
	}

	/**
	 * @param text
	 * @param iStart
	 * @param list
	 * @param pCountParams
	 * @return
	 */
	public static final int parseParameterList(String text, int iStart,
			String[] list, int pCountParams[])
	// text : (XXXX,XXXX,XXXX)
	{
		return parseParameterList(text, iStart, list, null, pCountParams);
	}

	/**
	 * @param text
	 * @param iStart
	 * @param list
	 * @param to
	 * @param pCountParams
	 * @return
	 */
	public static final int parseParameterList(String text, int iStart,
			String[] list, final java.util.List<String> to, int pCountParams[]) {
		if (list != null)
			for (int i = 0; i < list.length;)
				list[i++] = null;
		int jStack = 0;
		final int ltext = text.length();
		int nList = 0;
		int n = countStartSpace(text, iStart, ltext);
		if (iStart + n >= ltext || text.charAt(iStart + n) != '(')
			return 0;
		n++; // skip '(' ')'
		n += countStartSpace(text, iStart + n, ltext);
		char charStr = 0;
		int i0 = n;
		for (; iStart + n < ltext;) // for(;n<ltext;)
		{
			char c = text.charAt(iStart + n);
			if (c == '"' || c == '\'') {
				if (charStr == 0)
					charStr = c;
				else if (c == charStr && text.charAt(iStart + n - 1) != '\\')
					charStr = 0;
			}
			if (charStr != 0) {
				n++;
				continue;
			}
			if (jStack == 0 && (c == ',' || c == ')')) {
				String s = text.substring(iStart + i0, iStart + n).trim();
				if (s.length() > 0) {
					if (list != null)
						list[nList++] = s;
					if (to != null)
						to.add(s);
				} else if (c == ',' || nList > 0)
					throw new java.lang.IllegalArgumentException(text);
				n++;
				if (c == ')')
					break;
				i0 = n += countStartSpace(text, iStart + n, ltext);
				continue;
			}
			if (c == ')')
				jStack--;
			else if (c == '(')
				jStack++;
			n++;
		}
		if (pCountParams != null)
			pCountParams[0] = nList;
		return n;
	}

	
	/**
	 * 统计一个字符串中某位置开始连续的空格个数. 例如 countWhitespace("   abc  df",0) 结果为3
	 */
	public static final int countWhitespace(String text, int start) {
		int i = 0;
		for (; start + i < text.length()
				&& Character.isWhitespace(text.charAt(start + i)); i++)
			;
		return i;
	}

	/**
	 * 连接两个字符串 注意：第一个参数不能为null 示例1：StrUtil.strcat("abc", "bcd"); 结果：abcbcd
	 * 示例2：StrUtil.strcat("abc",null); 结果：abc
	 * 
	 * @param s1
	 *            字符串s1
	 * @param s2
	 *            字符串s2
	 * @return 两个字符串连接后的新字符串
	 */
	public static final String strcat(String s1, String s2) {
		return s1 != null && s2 != null ? s1 + s2 : (s1 == null ? s2 : s1);
	}

	/**
	 * 使用指定的连接符连接两个字符串，当两个字符串都为null时则返回null 注意：第一个参数不能为null
	 * 示例1：StrUtil.strcat("abc", "-","bcd"); 结果：abc-bcd
	 * 示例2：StrUtil.strcat("abc","-",null); 结果：abc
	 * 
	 * @param s1
	 *            字符串s1
	 * @param link
	 *            连接符
	 * @param s2
	 *            字符串s2
	 * @return 连接运算后的字符串
	 */
	public static final String strcat(String s1, String link, String s2) {
		return s1 != null && s2 != null ? s1 + link + s2 : (s1 == null ? s2
				: s1);
	}

	/**
	 * 将一个对象集合拼接成一个字符串，并在各个对象转成的字符串之间使用指定分隔符link隔开 List<String> list=new
	 * ArrayList<String>(); list.add("abc"); list.add("bcd"); list.add("efg");
	 * 示例1：StrUtil.strcat(list, "-"); 结果：abc-bcd-efg 示例2:StrUtil.strcat(list,
	 * "*"); 结果：abc*bcd*efg
	 * 
	 * @param list
	 *            要转成字符串的对象集合
	 * @param link
	 *            指定的分隔符
	 * @return 转换完成的字符串
	 */
	public static final String strcat(java.lang.Iterable list, String link) {
		if (list == null)
			return null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Object v : list) {
			if (i > 0)
				sb.append(link);
			sb.append(v);
			i++;
		}
		return sb.toString();
	}

	/**
	 * 使用指定的连接符连接两个字符串，当两个字符串都为null时则返回null 注意：第一个参数可以为null
	 * 示例1：StrUtil.strcat("abc",'-', "bcd"); 结果：abc-bcd
	 * 示例2：StrUtil.strcat(null,'-',"abc"); 结果：abc
	 * 
	 * @param s1
	 *            字符串s1
	 * @param link
	 *            连接符
	 * @param s2
	 *            字符串s2
	 * @return 连接运算后的字符串
	 */
	public static final String strcat(String s1, char link, String s2) {
		return s1 != null && s2 != null ? s1 + link + s2 : (s1 == null ? s2
				: s1);
	}

	/**
	 * 接字符串数组中的元素使用连接符连接起来，当数组中某元素为null时则使用nullStr将null替换然后继续连接
	 * 示例：StrUtil.strcat(new String[] {"abc","bcd","efg",null,"hij"},
	 * "-","PPP"); 结果：abc-bcd-efg-PPP-hij
	 * 
	 * @param a
	 *            字符串数组
	 * @param link
	 *            连接符
	 * @param nullStr
	 *            用于替换null值的字符串
	 * @return 连接完成的字符串
	 */
	public static final String strcat(String a[], String link, String nullStr) {
		String s = null;
		if (a != null)
			for (int i = 0; i < a.length; i++) {
				String s1 = a[i] == null ? nullStr : a[i];
				if (s1 == null)
					continue;
				s = s != null ? s + link + s1 : s1;
			}
		return s;
	}

	/**
	 * 将Object数组中的数据组合成为一个字符串，各对象之间使用字符link隔开 示例：StrUtil.objcat(new String[]
	 * {"abc","bcd","def"}, '-'); 结果：abc-bcd-def
	 * 
	 * @param a
	 *            要转换为字符串的Object数组
	 * @param link
	 *            各对象之间的分隔符
	 * @return 转换完成的字符串
	 */
	public static final String objcat(Object a[], char link) {
		return objcat(a, link, null);
	}

	public static final String listcat(java.lang.Iterable list, char link) {
		if (list == null)// || a.length==0 )
			return null;
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (final Object v : list) {
			if (i > 0)
				sb.append(link);
			sb.append(v);// a[i] == null ? : a[i]);
			i++;
		}
		return sb.toString();
	}

	/**
	 * 将Object数组中的数据组合成为一个字符串，各对象之间使用字符link隔开 示例：StrUtil.objcat(new String[]
	 * {"abc","bcd",null,"def"}, '-',"xxx"); 结果：abc-bcd-xxx-def
	 * 
	 * @param a
	 *            在转换为字符串的Object数组
	 * @param link
	 *            各对象之间的分隔符(字符)
	 * @param nullObj
	 *            默认值，如果对象为null，使用nullObj来替换
	 * @return 转换完成的字符串
	 */
	public static final String objcat(Object a[], char link, Object nullObj) {
		if (a == null)// || a.length==0 )
			return null;
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			if (i > 0)
				sb.append(link);
			sb.append(a[i] == null ? nullObj : a[i]);
		}
		return sb.toString();
	}

	

	public static final String objcat(java.util.List<?> a, char link,
			Object nullObj) {
		if (a == null)// || a.length==0 )
			return null;
		final StringBuilder sb = new StringBuilder();
		final int n = a.size();
		for (int i = 0; i < n; i++) {
			if (i > 0)
				sb.append(link);
			final Object v = a.get(i);
			sb.append(v == null ? nullObj : v);// a[i]);
		}
		return sb.toString();
	}

	/**
	 * 整形数组各元素之间均插入字符link 示例1：StrUtil.intcat(i,'*'); 结果：1*2*3
	 * 
	 * @param a
	 *            原数组
	 * @param link
	 *            要插入到数组中的字符
	 * @return 插入字符后的字符串
	 */
	public static final String intcat(int a[], char link) {
		if (a == null || a.length == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			if (i > 0)
				sb.append(link);
			sb.append(a[i]);// ==null?nullObj:a[i]);
		}
		return sb.toString();
	}

	/**
	 * 将Object数组中的数据组合成为一个字符串，各对象之间使用字符串link隔开 示例：StrUtil.objcat(new String[]
	 * {"abc","bcd",null,"def"}, "--","xxx"); 结果：abc--bcd--xxx--def
	 * 
	 * @param a
	 *            在转换为字符串的Object数组
	 * @param link
	 *            各对象之间的分隔符(字符串)
	 * @param nullObj
	 *            默认值，如果对象为null，使用nullObj来替换
	 * @return 转换完成的字符串
	 */
	public static final String objcat(Object a[], String link, Object nullObj) {
		if (a == null || a.length == 0)
			return null;
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			if (i > 0)
				sb.append(link);
			sb.append(a[i] == null ? nullObj : a[i]);
		}
		return sb.toString();
	}

	/**
	 * 将Object数组中的所有字符串元素清除前后的空格 示例：String[]
	 * s=(String[])StrUtil.stringArrayTrim(new String[]
	 * {"   abc","  bcd"," def"}); 结果：for(String str:s)
	 * System.out.println(str.length()); [abc,bcd,def] 一般该方法应用于字符串数组
	 * 
	 * @param a
	 *            Object数组
	 * @return 所有字符串元素前后都没有空格的字符串数组
	 */
	static public Object stringArrayTrim(Object a) {
		if (a instanceof Object[]) {
			Object oa[] = (Object[]) a;
			for (int i = 0; i < oa.length; i++) {
				if (oa[i] instanceof String)
					oa[i] = ((String) oa[i]).trim();
				else
					stringArrayTrim(oa[i]);
			}
		}
		return a;
	}

	/**
	 * 将一个字符串从某位置开始以某字符作为分隔符进行分隔(得到每段作为字符串的字符串数组). 示例： String list[] =
	 * Utilities.splitString("AAAA,BBBB,CCCC,DDDDD",0,',') 结果： list 为 {
	 * "AAAA","BBBB","CCCC","DDDD" }
	 * 
	 * @param str
	 *            被分隔的字符串
	 * @param istart
	 *            开始位置
	 * @param delimiter
	 *            分隔符
	 * @return 分隔的结果
	 */
	public static final String[] splitString(String str, int istart,
			char delimiter) {
		if (str == null)
			return null;
		int sl = str.length();
		int n = 0;
		for (int i = istart; i < sl; i++)
			if (str.charAt(i) == delimiter)
				n++;
		// if( n==0 )
		// return new String[]{str};
		String[] sa = new String[n + 1];
		int i = istart, j = 0;
		for (; i < sl;) {
			int iend = str.indexOf(delimiter, i);
			if (iend < 0)
				break;
			sa[j++] = str.substring(i, iend);
			i = iend + 1;
		}
		sa[j++] = str.substring(i);
		return sa;
	}

	/**
	 * 将字符串以指定分割符转换为字符串数组 示例1:StrUtil.splitString("abc\\bcd\\cde\\", '\\',
	 * true); 结果：[abc, bcd, cde, ]
	 * 
	 * @param str
	 *            要进行转换的字符串
	 * @param delimiter
	 *            分割符
	 * @param slash
	 *            目前未使用
	 * @return 分隔后的字符串数组
	 */
	public static final String[] splitString(String str, char delimiter,
			boolean slash) {
		if (str == null)
			return null;
		int sl = str.length();
		java.util.List<String> v = new ArrayList<String>();
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < sl; i++) {
			char c = str.charAt(i);
			char c0 = 0;
			if (c == '\\' && i < sl - 1) {
				char c2 = str.charAt(i + 1);
				if (c2 == '\\' || c2 == delimiter) {
					c0 = '\\';
					c = c2;
					i++;
				}
			}
			if (c == delimiter && c0 != '\\') {
				v.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				buffer.append(c);
			}
		}
		v.add(buffer.toString());
		return v.toArray(new String[v.size()]);
	}

	/**
	 * 将一个字符串以某字符作为分隔符进行分隔(得到每段作为字符串的字符串数组) 分隔效果同："123.345.678".split("\\.");
	 * 结果：[123, 345, 678] 示例1:StrUtil.splitString("abc.bcd.cde", '.'); 结果：[abc,
	 * bcd, cde]
	 * 
	 * @param str
	 *            被分隔的字符串
	 * @param delimiter
	 *            分隔符
	 * @return 分隔的结果
	 */
	public static final String[] splitString(String str, char delimiter) {
		if (delimiter == 0)
			return str == null ? null : new String[] { str };
		return splitString(str, 0, delimiter);
	}

	/**
	 * 注：该功能同subSplitString(String str, char delimiter, int index)
	 * 将一个字符串以某字符作为分隔符进行分隔后取其中某一段. 示例1：String subtext =
	 * Utilities.subString("AAAA,BBBB,CCCC,DDDDD",',',2); 结果：subtext 为 "CCCC"
	 * 
	 * @param str
	 *            被分隔的字符串
	 * @param delimiter
	 *            分隔符
	 * @param index
	 *            分隔后的子串数组下标
	 * @return 第 index 个子串
	 */
	public static final String subString(String str, char delimiter, int index) {
		return subSplitString(str, 0, delimiter, index);
	}

	/**
	 * 对字符串进行二次拆分得到一个字符串二维数组 示例： String[][] ss =
	 * StrUtil.splitString("name=tom;age=18;score=100", ';', '='); for (String[]
	 * s1 : ss) { System.out.print(Arrays.toString(s1)); } 结果：[name, tom][age,
	 * 18][score, 100]
	 * 
	 * @param str
	 *            要进行拆分的字符串
	 * @param delimiter1
	 *            分隔符1
	 * @param delimiter2
	 *            分隔符2
	 * @return 拆分完成的字符串二维数组
	 */
	public static final String[][] splitString(String str, char delimiter1,
			char delimiter2) {
		String[] a1 = splitString(str, delimiter1);
		if (a1 == null)
			return null;
		String a2[][] = new String[a1.length][];
		for (int i = 0; i < a1.length; i++) {
			a2[i] = splitString(a1[i], delimiter2);
		}
		return a2;
	}

	/**
	 * 将一个字符串以某字符作为分隔符进行分隔后取其中某一段. 示例1：String subtext =
	 * Utilities.splitString("AAAA,BBBB,CCCC,DDDDD",0,',',2); 结果： "CCCC"
	 * 
	 * @param str
	 *            被分隔的字符串
	 * @param istart
	 *            开始位置
	 * @param delimiter
	 *            分隔符
	 * @param index
	 *            分隔后的子串数组下标
	 * @return 第 index 个子串
	 */
	public static final String subSplitString(String str, int istart,
			char delimiter, int index) {
		if (str == null)
			return null;
		int sl = str.length();
		int i = istart, j = 0;
		for (; i < sl;) {
			int iend = str.indexOf(delimiter, i);
			if (iend < 0)
				break;
			if (j++ == index)
				return str.substring(i, iend);
			// System.out.println(sa[j-1]);
			i = iend + 1;
		}
		return j == index ? str.substring(i) : null;
	}

	/**
	 * 将一个字符串以某字符作为分隔符进行分隔后取其中某一段. 示例1：String subtext =
	 * StrUtil.splitString("AAAA,BBBB,CCCC,DDDDD",',',2); 结果：subtext 为 "CCCC"
	 * 
	 * @param str
	 *            被分隔的字符串
	 * @param delimiter
	 *            分隔符
	 * @param index
	 *            分隔后的子串数组下标
	 * @return 第 index 个子串
	 */
	public static final String subSplitString(String str, char delimiter,
			int index) {
		return subSplitString(str, 0, delimiter, index);
	}

	/**
	 * 返回子串在源字符串中的索引，如果找到则返回非负的索引，否则返回-1
	 * 
	 * @param str
	 *            源字符串
	 * @param delimiter
	 *            分隔符
	 * @param sub
	 *            子串
	 * @param trim
	 *            删除前后导空白字符
	 * @param ignoreCase
	 *            忽略大小写
	 * @return
	 */
	public static final int indexOf(String str, char delimiter, String sub,
			boolean trim, boolean ignoreCase) {
		if (str == null || sub == null)
			return -1;
		int p0 = 0;
		final int n = str.length();
		final int cmpLen = sub.length();
		int j = 0;
		for (int i = 0; i <= n; i++) {
			if (i == n || str.charAt(i) == delimiter) {
				if (trim) {
					if (ignoreCase ? str.substring(p0, i).trim()
							.equalsIgnoreCase(sub) : str.substring(p0, i)
							.trim().equals(sub))
						return j;
				} else {
					if (cmpLen == i - p0
							&& str.regionMatches(ignoreCase, p0, sub, 0, cmpLen))
						return j;
				}
				p0 = i + 1;
				j++;
			}
		}
		return -1;
	}

	

	/**
	 * 替换 \", \r, \n, \t, \\ 等 示例： System.out.println("\\abc\\n\\r");
	 * 结果：\abc\n\r
	 * System.out.println(StrUtil.replaceEscapeChar("\\abc\\n\\r"));结果：abc(回车换行)
	 * 
	 * @param text
	 *            要进行转换的字符串
	 */
	public static final String replaceEscapeChar(String text) {
		if (text == null)
			return text;
		StringBuilder textBuffer = new StringBuilder();
		int n = text.length();
		int m = 0;
		for (int i = 0; i < n; i++) {
			char c = text.charAt(i);
			if (c == '\\' && ++i < n) {
				m++;
				char c2 = text.charAt(i);
				if (c2 == 'n')
					c = '\n';
				else if (c2 == 'r')
					c = '\r';
				else
					c = c2;
			}
			textBuffer.append(c);
		}
		return m == 0 ? text : textBuffer.toString();
	}

	// forChars = "\"";
	/**
	 * 判断test中的每一个字符中否存在于forChars中，如果存在则在text里的该字符前插入"\"
	 * 示例1：StrUtil.insertEscapeChar("abc","b"); 结果：a\bc
	 * 示例2：StrUtil.insertEscapeChar("abc","cde"); 结果：ab\c
	 * 
	 * @param text
	 *            需要插入"\"的字符串
	 * @param forChars
	 *            被查找的字符串
	 * @return 新字符串
	 */
	public static final String insertEscapeChar(String text, String forChars) {
		if (text == null || forChars == null)
			return text;
		StringBuilder textBuffer = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (forChars.indexOf(c) >= 0) {
				textBuffer.append('\\');
			}
			textBuffer.append(c);
		}
		return textBuffer.toString();
	}

	
	public static final String getTextFromInputStream(java.io.InputStream is,
			String charSet) throws java.io.IOException {
		if (is == null)
			return null;
		try {
			is.reset();
		} catch (Exception ex) {
		}
		java.io.InputStreamReader r = charSet == null ? new java.io.InputStreamReader(
				is) : new java.io.InputStreamReader(is, charSet);
		StringBuilder buf = new StringBuilder();
		// try {
		for (;;) {
			int c = r.read();
			if (c <= 0)
				break;
			buf.append((char) c);
		}
		r.close();
		// } finally
		// {
		// r.close(); // is.close();
		// }
		return buf.toString();
	}

	/**
	 * 取出一个字符串中左边数字部分. 示例1：getLeftDigit("234XYB34JH",0); 结果：234
	 * 示例2：getLeftDigit("234XYB34JH",4); 结果： 示例3：getLeftDigit("234XYB34JH",2);
	 * 结果：4
	 * 
	 * @param str
	 *            要进行查询的字符串
	 * @return 字符串的左侧部分
	 */
	public static final String getLeftDigit(String str, int start) {
		if (str == null)
			return null;
		str = str.trim();
		int l = str.length();
		if (start > l)
			return null;
		int i;
		for (i = start; i < l; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9')
				break;
		}
		return str.substring(start, i);
	}

	/**
	 * 得到字符串左侧的数字部分 示例1：getLeftDigit("234XYB34JH"); 结果：234
	 * 
	 * @param str
	 *            要进行查询的字符串
	 * @return 字符串的左侧部分
	 */
	public static final String getLeftDigit(String str) {
		return getLeftDigit(str, 0);
	}

	/**
	 * 浮点型数据格式化，为人民币金额计算设计(注意:默认四舍五入) 示例1：StrUtil.rmbFormat(new
	 * BigDecimal(100.158));结果：100.16 示例2：StrUtil.rmbFormat(new
	 * BigDecimal(100.1));结果：100.10
	 * 
	 * @param x
	 *            要格式你给的浮点型数据
	 * @return 格式化后的字符串
	 */
	static public String rmbFormat(BigDecimal x) {
		return format(x, 2, 2, true);
	}

	/**
	 * 将一个数值格式化指定小数位、可含千位分节符的字符串.(注意:默认四舍五入) 示例1：format(new
	 * BigDecimal("3451234.5"),2,4,true);结果：3,451,234.50 示例2：format(new
	 * BigDecimal("3451234.55678"),2,4,true);结果：3,451,234.5568
	 * 
	 * @param x
	 *            被格式化的数值
	 * @param minDecimals
	 *            最小小数位
	 * @param maxDecimals
	 *            最大小数位
	 * @param groupingSeparator
	 *            是否含千位分节符
	 * @return 格式化后的结果
	 */
	static public String format(BigDecimal x, int minDecimals, int maxDecimals,
			boolean groupingSeparator) {
		if (x == null)
			return null;
		if (minDecimals < 0)
			minDecimals = 2;
		if (maxDecimals >= 0 && maxDecimals < x.scale())
			x = x.setScale(maxDecimals, BigDecimal.ROUND_HALF_UP);
		boolean neg = x.signum() < 0;
		if (neg)
			x = x.negate();
		int d = x.scale();
		String s = x.movePointRight(d).toString();
		if (maxDecimals > d) {
			s += newString('0', maxDecimals - d);
			d = maxDecimals;
		}
		int ls = s.length();
		if (ls > d)
			s = s.substring(0, ls - d) + '.' + s.substring(ls - d);
		else if (ls < d)
			s = "0." + newString('0', d - ls) + s;
		else
			s = "0." + s;
		return format(neg ? "-" + s : s, minDecimals, groupingSeparator);
	}

	/**
	 * 格式化字符串(数据字符)为指定小数位的字符串 示例1：StrUtil.format(1000000, 2, true);
	 * 结果：1,000,000.00 示例2：StrUtil.format(-1000000, 2, false); 结果：-1000000.00
	 * 
	 * @param str
	 *            被格式化的字符串
	 * @param minDecimals
	 *            保留的小数位
	 * @param groupingSeparator
	 *            是否有千分位分隔符
	 * @return 格式化之后的字符串
	 */
	final static String format(String str, int minDecimals,
			boolean groupingSeparator) {
		char tempBuf_64[] = new char[64];
		int n = str.length();
		int p = str.indexOf('.');
		if (p < 0)
			p = n;
		for (; n - 1 - p > minDecimals && n - 1 > p
				&& (str.charAt(n - 1) == '0'); n--)
			;
		if (n > 0 && str.charAt(n - 1) == '.')
			n--;
		int jBuf = 0;
		boolean neg = n > 0 && str.charAt(0) == '-';
		for (int i = 0; i < n; i++) {
			if (groupingSeparator && i > (neg ? 1 : 0) && i < p
					&& (p - i) % 3 == 0)
				tempBuf_64[jBuf++] = ',';
			tempBuf_64[jBuf++] = str.charAt(i);
		}
		return String.valueOf(tempBuf_64, 0, jBuf);
	}

	/**
	 * 将一个数值格式化指定小数位、可含千位分节符的字符串.例如:
	 * 示例1：format("3451234.5",2,4,true);结果：3,451,234.50
	 * 示例2：format("3451234.55678",2,4,true);结果：3,451,234.5568
	 * 
	 * @param x
	 *            被格式化的数值
	 * @param minDecimals
	 *            最小小数位
	 * @param maxDecimals
	 *            最大小数位
	 * @param groupingSeparator
	 *            是否含千位分节符
	 * @return 格式化后的字符串
	 */
	static public String format(double x, int minDecimals, int maxDecimals,
			boolean groupingSeparator) {
		return format(new BigDecimal(x), minDecimals, maxDecimals,
				groupingSeparator);
	}

	/**
	 * 将整数格式化为指定格式的字符串 示例1：format(1000000,3,true); 结果：1,000,000.000
	 * 示例2：format(1000000,-2,false); 结果：1000000
	 * 
	 * @param x
	 *            要被格式化的整数
	 * @param minDecimals
	 *            要保留的小数位，如果该值小于等于零，则不保留小数位
	 * @param groupingSeparator
	 *            是否格式化为有千位分节符的字符串
	 * @return 格式化后的字符串
	 */
	static public String format(int x, int minDecimals,
			boolean groupingSeparator) {
		return format(minDecimals <= 0 ? Integer.toString(x) : x + "."
				+ StrUtil.newString('0', minDecimals), minDecimals,
				groupingSeparator);
	}

	/**
	 * 将长整型格式化为指定格式的字符串 示例1：format(1000000L,3,true); 结果：1,000,000.000
	 * 示例2：format(1000000L,-1,false); 结果：1000000
	 * 
	 * @param x
	 *            要被格式化的长整型
	 * @param minDecimals
	 *            要保留的小数位，如果该值小于等于零，则不保留小数位
	 * @param groupingSeparator
	 *            是否格式化为有千位分节符的字符串
	 * @return 格式化后的字符串
	 */
	static public String format(long x, int minDecimals,
			boolean groupingSeparator) {
		return format(
				minDecimals <= 0 ? Long.toString(x) : x + "."
						+ StrUtil.newString('0', minDecimals), minDecimals,
				groupingSeparator);
	}

	/**
	 * 将长整型整数格式化为指定格式的字符串 示例1：format(1000000,true); 结果：1,000,000
	 * 示例2：format(1000000,false); 结果：1000000
	 * 
	 * @param x
	 *            要被格式化的长整型
	 * @param groupingSeparator
	 *            是否格式化为有千位分节符的字符串
	 * @return 格式化后的字符串
	 */
	static public String format(long x, boolean groupingSeparator) {
		return format(Long.toString(x), 0, groupingSeparator);
	}

	/**
	 * 搜索指定字符中字符串中第一次出现的位置 示例1：StrUtil.startWithInt("-123abc", 'a', true); 结果：4
	 * 示例2：StrUtil.startWithInt("-123xabc", 'a', true); 结果：-1
	 * 示例3：StrUtil.startWithInt("-123abc", 'a', false); 结果：-1
	 * 
	 * @param s
	 *            要进行搜索的字符串
	 * @param charBefore
	 *            要在字符串中搜索的字符
	 * @param negEnable
	 *            该字符串第一个字符是否为'-'
	 * @return 如果该字符存在字符串中且该字符之前的所有字符都为'0'到'9'则返回该字符出现的位置 否则返回-1
	 */
	public static int startWithInt(String s, char charBefore, boolean negEnable) {
		int p = s.indexOf(charBefore);
		if (p <= 0)
			return p;
		int i = 0;
		if (negEnable && s.charAt(0) == '-')
			i++;
		for (; i < p; i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9')
				return -1;
		}
		return p;
	}

	/**
	 * 分析一个字符串表示的整数. 0x作为前缀的看成16进制, 0b作为前缀的看成二进制(字母不区分大小写).
	 * 示例1：parseInt("0x1F"); 结果为 31 示例2：parseInt("0b1011011"); 结果为 91
	 */
	static public final int parseInt(String text) {
		text = text.trim();
		int start = 0, base = 10;
		if (text.startsWith("0x") || text.startsWith("0X")) {
			start = 2;
			base = 16;
		} else if (text.startsWith("0b") || text.startsWith("0B")) {
			start = 2;
			base = 2;
		}
		return Integer.parseInt(text.substring(start), base);
	}

	/**
	 * 分析一个字符串表示的整数. 0x作为前缀的看成16进制, 0b作为前缀的看成二进制(字母不区分大小写).
	 * 示例1：parseLong("0x1F"); 结果为 31 示例2：parseLong("0b1011011"); 结果为 91
	 */
	static public final long parseLong(String text) {
		text = text.trim();
		int start = 0, base = 10;
		if (text.startsWith("0x") || text.startsWith("0X")) {
			start = 2;
			base = 16;
		} else if (text.startsWith("0b") || text.startsWith("0B")) {
			start = 2;
			base = 2;
		}
		return Long.parseLong(text.substring(start), base);
	}

	/**
	 * 格式化用逗号分开的查询条件. 例如 <blockquote>
	 * 
	 * <pre>
	 * filterFormat("OR","acode LIKE '%0'",null,"1*,3*,5*")
	 * // 结果为 "acode LIKE '1*' OR acode LIKE '3*' OR acode LIKE '5*'
	 * filterFormat("OR","acode LIKE '%0'","acode between '%0' and '%1'","1*,3*,5-7")
	 * // 结果为 "acode LIKE '1*' OR acode LIKE '3*' OR acode between '5' and '7'
	 * @param link 查询条件的连接串， 一般为 "OR","AND" 等
	 * @param fmt1 只含一个参数的格式
	 * @param fmt2 含两个参数的格式
	 * @param parms 逗号分开的参数表作为筛选,如"1*,3*,5*"
	 * @param brackets 参数表中多个参数时是否将结果字符串加括号
	 * @return 格式化后的结果
	 */
	public static String filterFormat(String link, String fmt1, String fmt2,
			String parms, boolean brackets) {
		return filterFormat(link, fmt1, fmt2, splitString(parms, ','), brackets);
	}

	public static String filterFormat(String link, String fmt1, String fmt2,
			String parms, int options) {
		return filterFormat(link, fmt1, fmt2, splitString(parms, ','), options);
	}

	public static String filterFormat(String link, String fmt1, String fmt2,
			String parm[], boolean brackets) {
		return filterFormat(link, fmt1, fmt2, parm, brackets ? 1 : 0);
	}

	/**
	 * 格式化查询条件. 例子：
	 * "select * from emails where emicode in ("+StrUtil.filterFormat
	 * (",","'%0'",null,emicodeA,2)+")"
	 * 
	 * @param link
	 *            查询条件的连接串， 一般为 "OR","AND" 等
	 * @param fmt1
	 *            只含一个参数的格式
	 * @param fmt2
	 *            含两个参数的格式
	 * @param parm
	 *            参数表, 如 new String[]{"1*","2*","5*"}
	 * @param options
	 *            bit 1: 参数表中多个参数时是否将结果字符串加括号 2: link 前后不加空格 4 : trim parm[?}
	 * @return 格式化后的结果
	 */
	public static String filterFormat(String link, String fmt1, String fmt2,
			String parm[], int options) {
		if (parm == null)
			return null;
		String r = null;
		for (int i = 0; i < parm.length; i++) {
			String s = (options & 4) != 0 ? parm[i].trim() : parm[i];
			int p = fmt2 == null ? -1 : s.indexOf('-');
			if (p > 0)
				s = format(fmt2, s.substring(0, p), s.substring(p + 1));
			else
				s = format(fmt1, s);
			if (i == 0)
				r = s;
			else
				r += ((options & 2) != 0 ? link : ' ' + link + ' ') + s;
		}
		return (options & 1) != 0 && parm.length > 1 ? "(" + r + ")" : r;
	}

	/**
	 * 格式化查询条件.
	 * 
	 * @param link
	 *            查询条件的连接串， 一般为 "OR","AND" 等
	 * @param fmt1
	 *            只含一个参数的格式
	 * @param fmt2
	 *            含两个参数的格式
	 * @param parms
	 *            逗号分开的参数表作为筛选,如"1*,3*,5*"
	 * @return 格式化后的结果
	 */
	public static String filterFormat(String link, String fmt1, String fmt2,
			String parms) {
		return filterFormat(link, fmt1, fmt2, splitString(parms, ','), false);
	}

	/**
	 * 格式化查询条件.
	 * 
	 * @param link
	 *            查询条件的连接串， 一般为 "OR","AND" 等
	 * @param fmt1
	 *            只含一个参数的格式
	 * @param fmt2
	 *            含两个参数的格式
	 * @param parm
	 *            参数表, 如 new String[]{"1*","2*","5*"}
	 * @return 格式化后的结果
	 */
	public static String filterFormat(String link, String fmt1, String fmt2,
			String parm[]) {
		return filterFormat(link, fmt1, fmt2, parm, false);
	}

	static final public String filterFormat(String link, String fmt1,
			String fmt2, int value[]) {
		return filterFormat(link, fmt1, fmt2, value, false);
	}

	static final public String filterFormat(String link, String fmt1,
			String fmt2, int value[], boolean brackets) {
		return filterFormat(link, fmt1, fmt2, value, brackets ? 1 : 0);
	}

	/**
	 * 格式化查询条件.
	 * 
	 * @param link
	 *            查询条件的连接串， 一般为 "OR","AND" 等
	 * @param fmt1
	 *            只含一个参数的格式
	 * @param fmt2
	 *            含两个参数的格式
	 * @param parm
	 *            参数表, 如 new int[]{1,2,5}
	 * @return 格式化后的结果
	 */
	static final public String filterFormat(String link, String fmt1,
			String fmt2, int value[], int options) {
		if (value == null)
			return null;
		String r = null;
		int n = 0;
		for (int i = 0; i < value.length;) {
			int j = i + 1;
			if (fmt2 != null)
				for (; j < value.length && value[j] == value[j - 1] + 1; j++)
					;
			String s;
			if (j - 1 == i)
				s = format(fmt1, Integer.toString(value[i]));
			else
				s = format(fmt2, Integer.toString(value[i]),
						Integer.toString(value[j - 1]));
			if (i == 0)
				r = s;
			else
				r += ((options & 2) != 0 ? link : ' ' + link + ' ') + s;
			n++;
			i = j;
		}
		return (options & 1) != 0 && n > 1 ? "(" + r + ")" : r;
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的模式串通配. 例如 like("123A45","1*A??",true) 为true,
	 * like("123A45","1*A???",true) 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的模式串
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean like(String text, String pattern, boolean ignoreCase) {
		return like(text, 0, pattern, 0, ignoreCase);
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的模式串通配(大小写敏感). 例如 like("123A45","1*A??") 为true,
	 * like("123A45","1*A???") 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的模式串
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean like(String text, String pattern) {
		return like(text, 0, pattern, 0, false);
	}

	/**
	 * 判断一个字符串(从某位置起)是否与一含通配符(*,?)的模式串(从某位置起)通配. 例如 like("123A45","1*A??",true)
	 * 为true, like("123A45","1*A???",true) 为 false
	 * StrUtil.like("01002","010%02%%") = true StrUtil.like("01002","010%01%%")
	 * = false StrUtil.like("123A45","1*A??") StrUtil.like("123A45","1*A???")
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param oText
	 *            text的起始位置
	 * @param pattern
	 *            含通配符(*,?)的模式串
	 * @param oPattern
	 *            pattern的起始位置
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean like(String text, int oText, String pattern,
			int oPattern, boolean ignoreCase) {
		if (text == null)
			return pattern == null; // 2002-11-08
		final int ltext = text.length() - oText;
		final int lpattern = pattern.length() - oPattern;
		for (int i = 0; i < lpattern /* && i<ltext */; i++) {
			char c = pattern.charAt(oPattern + i);
			if (c == '*' || c == '%') {
				if (i == lpattern - 1
						|| endsWithStarsPattern(pattern, oPattern + i))
					return true;
				for (int iText = oText + i; iText < ltext + oText; iText++)
					if (like(text, iText, pattern, oPattern + i + 1, ignoreCase))
						return true;
				return false;
			}
			if (i >= ltext)
				return false;
			if (c == '?' || c == '_') {
				continue;
			}
			if (ignoreCase) {
				if (Character.toUpperCase(c) != Character.toUpperCase(text
						.charAt(oText + i)))
					return false;
			} else {
				if (c != text.charAt(oText + i))
					return false;
			}
		}
		return ltext == lpattern
				|| (lpattern >= ltext + 1 && endsWithStarsPattern(pattern,
						ltext));
	}

	final static private boolean endsWithStarsPattern(String text, int from) {
		if (from < 0)
			from = 0;
		for (; from < text.length(); from++) {
			char c = text.charAt(from);
			if (c != '*' && c != '%')
				return false;
		}
		return true;
	}

	/**
	 * 求给定字符串中有效字符个数。有效字符指：除去* % ？ _ 四个字符以外的所有字符
	 * 示例：StrUtil.countNonWildChars("abc^&%*"); 结果：5
	 * 
	 * @param text
	 *            要查询的字符串
	 * @return 字符串中有效字符个数
	 */
	public final static int countNonWildChars(String text) {
		if (text == null)
			return 0;
		final int n = text.length();
		int count = 0;
		for (int i = 0; i < n; i++) {
			char c = text.charAt(i);
			if (c != '*' && c != '%' && c != '?' && c != '_')
				count++;
		}
		return count;
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的若干模式串(用逗号分开)中的一个(加上前后缀)通配. 例如
	 * like("123A45","1*B??,1*A??",true) 为true,
	 * like("123A45","1*B??,1*A???",true) 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的若干模式串(用逗号分开),
	 * @param patternPrifix
	 *            前缀
	 * @param patternSuffix
	 *            后缀
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean likeOneOf(String text, String pattern,
			String patternPrifix, String patternSuffix, boolean ignoreCase) {
		return likeOneOf(text, pattern, ',', patternPrifix, patternSuffix,
				ignoreCase);
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的若干模式串(用deli分开)中的一个(加上前后缀)通配. 例如
	 * like("123A45","1*B??,1*A??",true) 为true,
	 * like("123A45","1*B??,1*A???",true) 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的若干模式串(用deli分开),
	 * @param deli
	 *            pattern中的分割符
	 * @param patternPrifix
	 *            前缀
	 * @param patternSuffix
	 *            后缀
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean likeOneOf(String text, String pattern, char deli,
			String patternPrifix, String patternSuffix, boolean ignoreCase) {
		if (patternPrifix == null)
			patternPrifix = "";
		if (patternSuffix == null)
			patternSuffix = "";
		String patternlist[] = splitString(pattern, deli);
		for (int i = 0; i < patternlist.length; i++)
			if (like(text, 0, patternPrifix + patternlist[i] + patternSuffix,
					0, ignoreCase))
				return true;
		return false;
	}

	/**
	 * @param text
	 * @param pattern
	 * @param deli
	 * @param patternPrifix
	 * @param patternSuffix
	 * @param ignoreCase
	 * @return
	 */
	public static boolean likeOneOfN(String text, String pattern, char deli,
			String patternPrifix, String patternSuffix, boolean ignoreCase) {
		return likeOneOfN(text, splitString(pattern, deli), patternPrifix,
				patternSuffix, ignoreCase);
	}

	/**
	 * @param text
	 * @param patternlist
	 * @param ignoreCase
	 * @return
	 */
	public static boolean likeOneOfN(String text, String patternlist[],
			boolean ignoreCase) {
		return likeOneOfN(text, patternlist, null, null, ignoreCase);
	}

	/**
	 * 
	 */
	public static boolean likeOneOfN(String text, String patternlist[],
			String patternPrifix, String patternSuffix, boolean ignoreCase) {
		if (patternPrifix == null)
			patternPrifix = "";
		if (patternSuffix == null)
			patternSuffix = "";
		boolean like = false;
		for (int i = 0; i < patternlist.length; i++) {
			String s = patternlist[i];
			boolean k = true;
			if (s.length() > 0 && s.charAt(0) == '~') {
				k = false;
				s = s.substring(1);
			}
			if (like(text, 0, patternPrifix + s + patternSuffix, 0, ignoreCase)) {
				like = k;
			}
		}
		return like;
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的若干模式串(用逗号分开)中的一个通配. 例如
	 * like("123A45","1*B??,1*A??",true) 为true,
	 * like("123A45","1*B??,1*A???",true) 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的若干模式串(用逗号分开)
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean likeOneOf(String text, String pattern,
			boolean ignoreCase) {
		return likeOneOf(text, splitString(pattern, ','), ignoreCase);
	}

	/**
	 * 判断包含N个匹配符的字符串数组中是否有和字符串text匹配上的字符串
	 * 
	 * @param text
	 *            需要匹配的字符串
	 * @param patternlist
	 *            包含N个匹配符的字符串数组
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 只要有一个能匹配上则返回true，否则返回false
	 */
	public static final boolean likeOneOf(String text, String[] patternlist,
			boolean ignoreCase) {
		if (patternlist == null)
			return false;
		// System.err.println("patternlist="+patternlist.length+":"+StrUtil.strcat(patternlist,",",null));
		for (int i = 0; i < patternlist.length; i++)
			if (like(text, 0, patternlist[i], 0, ignoreCase))
				return true;
		return false;
	}

	/**
	 * 判断包含N个匹配符的字符串数组中是否有和字符串数组texta匹配上的字符串
	 * 
	 * @param text
	 *            需要匹配的字符串数组
	 * @param patternlist
	 *            包含N个匹配符的字符串数组
	 * @param ignoreCase
	 *            是否忽略字母的大小写
	 * @return 只要有一个能匹配上则返回true，否则返回false
	 */
	public static final boolean likeOneOf2(String texta[],
			String[] patternlist, boolean ignoreCase) {
		if (texta == null)
			return false;
		for (int i = 0; i < texta.length; i++) {
			if (likeOneOf(texta[i], patternlist, ignoreCase))
				return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的若干模式串(用逗号分开)中的一个通配(大小写敏感). 例如
	 * like("123A45","1*B??,1*A??",true) 为true,
	 * like("123A45","1*B??,1*A???",true) 为 false
	 * 
	 * @param text
	 *            被判断的字符串
	 * @param pattern
	 *            含通配符(*,?)的若干模式串(用逗号分开)
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	public static boolean likeOneOf(String text, String pattern) {
		return likeOneOf(text, pattern, false);
	}

	/**
	 * @param text
	 * @param pattern
	 * @param deli
	 * @return
	 */
	public static boolean likeOneOf(String text, String pattern, char deli) {
		return likeOneOf(text, pattern, deli, (String) null, (String) null,
				false);
	}

	/**
	 * 判断包含N个匹配符的字符串数组中是否有和字符串text匹配上的字符串，大小写敏感
	 * 
	 * @param text
	 *            需要匹配的字符串
	 * @param patternlist
	 *            包含N个匹配符的字符串数组
	 * @return 只要有一个能匹配上则返回true，否则返回false
	 */
	public static boolean likeOneOf(String text, String[] patternlist) {
		return likeOneOf(text, patternlist, false);
	}

	/**
	 * 检查一个字符串中是否是一个有效的数值 示例1：StrUtil.isValidNumber("10000.5",1); 结果：true
	 * 示例2：StrUtil.isValidNumber("100,000",2); 结果：true
	 * 示例3：StrUtil.isValidNumber("-100000",4); 结果：true
	 * 示例4：StrUtil.isValidNumber(" ",8); 结果：true
	 * 示例5：StrUtil.isValidNumber("10000..20000",16); 结果：true
	 * 示例6：StrUtil.isValidNumber("-10,000.5..20000",23) 结果：true,分析：1+2+4+16=23
	 * 
	 * @param text
	 *            检查的字符串
	 * @param flags
	 *            位表示的检查选项 bit 1 : 可含'.' , bit 2 :可含',', bit 4 : 可含'-', bit 8 :
	 *            可为空,16: 可以是范围
	 * @return true : 如果检查合法, false : 如果检查不合法
	 */
	static public boolean isValidNumber(String text, int flags) {
		if (text == null)
			return false;
		if ((flags & 16) != 0) {
			int p = text.indexOf("..");
			if (p >= 0) {
				String text1 = text.substring(0, p).trim();
				String text2 = text.substring(p + 2).trim();
				if (text1.length() == 0 || text2.length() == 0)
					return false;
				return isValidNumber(text1, flags & ~16)
						&& isValidNumber(text2, flags & ~16);
			}
		}
		int lText = text.length();
		int i = 0;
		for (; i < lText && text.charAt(i) <= ' '; i++)
			;
		if (i == lText)
			return (flags & 8) != 0;
		if (text.charAt(i) == '-') {
			if ((flags & 4) == 0 || ++i >= lText)
				return false;
		}
		int ndig = 0;
		for (; i < lText; i++) {
			char c = text.charAt(i);
			if (c >= '0' && c <= '9') {
				ndig++;
				continue;
			}
			if (c == ',' && (flags & 2) != 0)
				continue;
			break;
		}
		if (ndig == 0)
			return false;
		if (i < lText && text.charAt(i) == '.') {
			if ((flags & 1) == 0)
				return false;
			i++;
			for (; i < lText; i++) {
				char c = text.charAt(i);
				if (c < '0' || c > '9')
					break;
			}
		}
		for (; i < lText && text.charAt(i) <= ' '; i++)
			;
		return i == lText;
	}

	/** 数组中不区分大小写地查找字符串 */
	public static int findAtStringArrayIgnoreCase(String a[], String o) {
		if (a != null)
			for (int i = 0; i < a.length; i++)
				if (o == a[i] || (o != null && o.equalsIgnoreCase(a[i])))
					return i;
		return -1;
	}

	

	/**
	 * 把对象转换为字符串输出，该对象建议实现toString()方法 示例：StrUtil.obj2str("abc"); 结果：abc
	 * 
	 * @param text
	 *            要转换的对象
	 * @return 转换为的字符串
	 */
	static public final String obj2str(Object text) {
		return text != null ? text.toString() : null;
	}
	
	

	/**
	 * 去掉一个字符串右边的空格. 示例1：StrUtil.trimRight("abc   ").length(); 结果：3
	 * 
	 * @param text
	 *            要进行处理的字符串
	 * @return 返回去掉右侧空字符的新字符串
	 */
	public static final String trimRight(String text) {
		if (text == null)
			return null;
		int l = text.length();
		for (; l > 0 && text.charAt(l - 1) <= ' '; l--)
			;
		return l == text.length() ? text : text.substring(0, l);
	}

	/**
	 * 比较两个字符串是否相等，忽略text1字符串右侧的空格
	 * 示例：StrUtil.equalsIgnorRightSpc("abc   ","abc"); 结果：true
	 * 
	 * @param text1
	 *            参加比较的字符串
	 * @param text2
	 *            参加比较的字符串
	 * @return 是否相等
	 */
	public static final boolean equalsIgnorRightSpc(String text1, String text2) {
		if (text1 == text2)
			return true;
		if (text1 == null || text2 == null)
			return false;
		return trimRight(text1).equals(text2);
	}

	/**
	 * 在字符串中指定字符before前插入指定字符insert 示例1：StrUtil.insertBeforeChar("abc", 'd',
	 * 'b')); 结果：adbc 示例2：StrUtil.insertBeforeChar("abc", 'd', 'e')); 结果：abc
	 * 
	 * @param text
	 *            要插入字符的字符串
	 * @param insert
	 *            要插入的字符
	 * @param before
	 *            字符串中原有字符，将在其前插入新字符
	 * @return 新字符串
	 */
	static final public String insertBeforeChar(String text, char insert,
			char before) {
		if (text == null)
			return null;
		StringBuilder buffer = new StringBuilder();
		int l = text.length();
		for (int i = 0; i < l; i++) {
			char c = text.charAt(i);
			if (c == before)
				buffer.append(insert);
			buffer.append(c);
		}
		return buffer.toString();
	}

	/**
	 * 用于将Boolean类型和String类型转化为基本类型boolean 示例1：StrUtil.obj2bool("true"); 结果：true
	 * 示例2：StrUtil.obj2bool(new Boolean(true)); 结果:true
	 * 示例3：StrUtil.obj2bool("abc"); 结果：false
	 * 
	 * @param o
	 *            要转化的对象
	 * @return 返回boolean,如果传入的对象类型不匹配，则返回默认值false
	 */
	static public final boolean obj2bool(Object o) {
		return obj2bool(o, false);
	}

	/**
	 * 用于将Boolean类型和String类型转化为基本类型boolean 示例1：StrUtil.obj2bool("true",false);
	 * 结果：true 示例2：StrUtil.obj2bool(new Boolean(true),false); 结果:true
	 * 示例3：StrUtil.obj2bool("abc",false); 结果：false
	 * 
	 * @param o
	 *            要转化的对象
	 * @param defaulValue
	 *            如果传入的对象类型不匹配，则返回默认值defaultValue
	 * @return 返回boolean
	 */
	static public final boolean obj2bool(Object o, boolean defaulValuet) {
		if (o != null) {
			if (o instanceof Boolean)
				return ((Boolean) o).booleanValue();
			if (o instanceof String) {
				String s = (String) o;
				if (s.equalsIgnoreCase("true"))
					return true;
				if (s.equalsIgnoreCase("false"))
					return false;
			}
		}
		return defaulValuet;
	}

	/**
	 * 抽取 字符串中的 数字
	 * 
	 * @param opts
	 *            opts#1 -- 含 小数点 #2 -- 可以为 负数
	 */
	static public String extractNumberStr(String s, int opts) {
		if (s == null)
			return null;
		StringBuilder sb = new StringBuilder();
		final int n = s.length();
		int nd = 0;
		for (int i = 0; i < n; i++) {
			final char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				sb.append(c);
				nd++;
				continue;
			}
			if (c == '-' && (opts & 2) != 0 && sb.length() == 0) {
				opts &= ~2;
				sb.append(c);
				continue;
			}
			if (c == '.' && (opts & 1) != 0) {
				opts &= ~1;
				sb.append(c);
				continue;
			}
			if (c > ' ' && sb.length() > 0)
				break;
		}
		if (nd == 0)
			return null;
		return sb.toString();
	}

	/**
	 * 从一个对象转化为一个整数 示例1：StrUtil.obj2int("abc",100); 结果：100
	 * 示例2：StrUtil.obj2int("200",100); 结果：200
	 * 
	 * @param o
	 *            Number,String 类型的对象
	 */
	static public final int obj2int(Object o, int defaultValue) {
		if (o instanceof Number)
			return ((Number) o).intValue();
		if (o instanceof String) {
			final String s = ((String) o).trim();
			try {
				return Integer.parseInt(s);
			} catch (Exception ex) {
			}
			{
				try {
					return Double.valueOf(s).intValue();
				} catch (Exception ex) {
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 用于将Double类型和String类型转化为基本类型double 示例1：StrUtil.obj2double("100"); 结果：100.0
	 * 示例2：StrUtil.obj2double(new Double(10000.00)); 结果：10000.0
	 * 示例3：StrUtil.obj2double(100); 结果：100.0 示例4：StrUtil.obj2double("abc");
	 * 结果：0.0
	 * 
	 * @param o
	 *            要转化的对象
	 * @return double类型数据,如果传入的参数类型不正确，则返回默认值0
	 */
	static public final double obj2double(Object o) {
		return obj2double(o, 0);
	}

	/**
	 * 用于将Double类型和String类型转化为基本类型double 示例1：StrUtil.obj2double("100",0);
	 * 结果：100.0 示例2：StrUtil.obj2double(new Double(10000.00),0); 结果：10000.0
	 * 示例3：StrUtil.obj2double(100,0); 结果：100.0 示例4：StrUtil.obj2double("abc",10);
	 * 结果：10.0
	 * 
	 * @param o
	 *            要转化的对象
	 * @param defaultValue
	 *            如果传入的参数类型不正确，则返回默认值
	 * @return double类型数据
	 */
	static public final double obj2double(Object o, double defaultValue) {
		if (o instanceof Number)
			return ((Number) o).doubleValue();
		if (o instanceof String) {
			String s = ((String) o).trim();
			try {
				return Double.parseDouble(s);
			} catch (Exception ex) {
			}
		}
		return defaultValue;
	}

	/**
	 * 将一个对象转换为一个长整型 示例：StrUtil.obj2long("1024",100); 结果：1024
	 * 
	 * @param o
	 *            要进行转换的对象
	 * @param defaultValue
	 *            默认值，对象不符合转换条件时返回
	 * @return 返回的长整型
	 */
	static public final long obj2long(Object o, long defaultValue) {
		if (o instanceof Number)
			return ((Number) o).longValue();
		if (o instanceof String) {
			String s = ((String) o).trim();
			// [add] by wlp 2014-01-23
			if (s.endsWith("L") || s.endsWith("l")) {
				s = s.substring(0, s.length() - 1);
			}
			// [end add]
			try {
				return Long.parseLong(s);
			} catch (Exception ex) {
			}
			// if( s.indexOf('.')>=0 )
			{
				try {
					return Double.valueOf(s).longValue();
				} catch (Exception ex) {
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 将对象数组中具体某一个对象转成一个整数 示例：StrUtil.obj2int(new Integer[] {10,20,null},2,100);
	 * 结果：100
	 * 
	 * @param o
	 *            对象数组
	 * @param j
	 *            进行转化的数组中某个对象的下标
	 * @param defaultValue
	 *            默认值，当该对象不符合转换条件时返回
	 * @return 转化后的整数
	 */
	static public final int obj2int(Object o[], int j, int defaultValue) {
		if (o == null || j < 0 || j >= o.length)
			return defaultValue;
		return obj2int(o[j], defaultValue);
	}

	/**
	 * 将Object数组中所有元素转换为整型 示例：Arrays.toString(StrUtil.obja2inta(new Integer[]
	 * {10,20,null},100)); 结果：[10, 20, 100]
	 * 
	 * @param o
	 *            要进行转换的Object数组
	 * @param defaultValue
	 *            默认值，当某对象不符合条件时用默认值替换
	 * @return 转换后的整形数组
	 */
	static public final int[] obja2inta(Object o[], int defaultValue) {
		if (o == null)
			return null;
		int a[] = new int[o.length];
		for (int i = 0; i < o.length; i++)
			a[i] = obj2int(o[i], defaultValue);
		return a;
	}

	/**
	 * 将二维Object数组中所有元素转换为整型 示例：StrUtil.obja2inta(new Integer[][] {{10,20,null},
	 * {20,30,40}},100); 结果：[[10,20,100],[20,30,40]]
	 * 
	 * @param o
	 *            要进行转换的二维Object数组
	 * @param defaultValue
	 *            默认值，当某对象不符合条件时用默认值替换
	 * @return 转换后的二维整形数组
	 */
	static public final int[][] obja2inta(Object o[][], int defaultValue) {
		if (o == null)
			return null;
		int a[][] = new int[o.length][];
		for (int i = 0; i < o.length; i++)
			a[i] = obja2inta(o[i], defaultValue);
		return a;
	}

	/**
	 * 将指定对象转换为整型 示例1：StrUtil.obj2int("1000.0"); 结果：1000 示例2：StrUtil.obj2int(new
	 * Integer(1000); 结果：1000 示例3: StrUtil.obj2int("abc"); 结果:0
	 * 
	 * @param o
	 *            要进行转换的对象
	 * @return 转换后的整数，如果对象格式不正确则返回0
	 */
	static public final int obj2int(Object o) {
		return obj2int(o, 0);
	}

	/*
	 * static public int parseInt0(String text) { try { return
	 * Integer.parseInt(text); } catch( Exception ex ) { } return 0; }
	 */
	/**
	 * 将一个字符串加入到指定字符串数组的指定下标index位置 示例1：StrUtil.addStringArrayElement(new
	 * String[] {"a","b","c"}, "d", 2); 结果：[a, b, d]
	 * 示例2：StrUtil.addStringArrayElement(new String[] {"a","b","c"}, "d", 3);
	 * 结果：[a, b, c, d] 示例3：StrUtil.addStringArrayElement(new String[]
	 * {"a","b","c"}, "d", 5); 结果：[a, b, c, null, null, d]
	 * 
	 * @param a
	 *            源字符串数组
	 * @param value
	 *            要插入的字符串值
	 * @param index
	 *            指定插入的下标位置
	 * @return 插入新字符串后的新的数组
	 */
	static public String[] addStringArrayElement(String[] a, String value,
			int index) {
		if (index == -1)
			index = a == null ? 0 : a.length;
		if (index < 0)
			throw new IllegalArgumentException();
		if (a == null || a.length <= index) {
			String[] old = a;
			a = new String[index + 1];
			if (old != null)
				System.arraycopy(old, 0, a, 0, old.length);
		}
		a[index] = value;
		return a;
	}

	/**
	 * 求两个字符数组的交集 示例：
	 * 
	 * @param a1
	 *            第一个字符数组
	 * @param a2
	 *            第二个字符数组
	 * @param mode
	 *            ==0: a1 与 a2 完全相同的元素, ==1: 考虑级次后的相同的元素
	 * @return
	 */
	static public String[] createIntersection(String a1[], String a2[], int mode) {
		if (a1 == null || a2 == null)
			return null;
		if (a1 == a2)
			return a1;
		final java.util.List<String> v = new java.util.ArrayList<String>();
		createIntersection(a1, a2, mode, v, false);
		return v.toArray(new String[v.size()]);
	}

	/**
	 * 判断两个数组是否存在交集
	 * 
	 * @param a1
	 *            数组1
	 * @param a2
	 *            数组2
	 * @param mode
	 *            是否判断数组中每个元素(字符串)的子串是否包含在另一个数组中：1：判断
	 * @return 两个数组是否存在交集
	 */
	static public boolean hasIntersection(String a1[], String a2[], int mode) {
		if (a1 == null || a2 == null)
			return false;
		if (a1 == a2)
			return a1.length > 0;
		java.util.List<String> v = new ArrayList<String>();
		createIntersection(a1, a2, mode, v, false);
		return v.size() > 0;
	}

	static final private void createIntersection(String a1[], String a2[],
			int mode, java.util.List<String> v, boolean forHas) {
		a1 = (String[]) a1.clone();
		Arrays.sort(a1);// ,sc);
		a2 = (String[]) a2.clone();
		Arrays.sort(a2);// ,sc);
		for (int i = 0; i < a1.length; i++) {
			String s = a1[i];
			if (s != null) {
				int j = Arrays.binarySearch(a2, s);// ,sc);
				if (j < 0 && mode == 1) {
					for (int l = s.length() - 1; l > 0; l--)
						if ((j = Arrays.binarySearch(a2, s.substring(0, l))) >= 0)
							break;
				}
				if (j >= 0) {
					v.add(s);
					if (forHas)
						return;
				}
			}
		}
		if (mode == 1)
			for (int i = 0; i < a2.length; i++) {
				String s = a2[i];
				if (s == null)
					continue;
				int j = -1;
				for (int l = s.length() - 1; l > 0; l--)
					if ((j = Arrays.binarySearch(a1, s.substring(0, l))) >= 0)
						break;
				if (j >= 0) {
					v.add(s);
					if (forHas)
						return;
				}
			}
	}

	
	

	/**
	 * 将一个byte数组转换为一个十六进制的字符串 示例1：StrUtil.toHexString(new byte[]
	 * {127,1,15,-128,0}); 结果：7F010F8000 示例2：StrUtil.toHexString(new byte[]
	 * {10,20,30,40,50}); 结果：0A141E2832
	 * 
	 * @param a
	 *            要进行转换的byte数组
	 * @return 转换完成的字符串
	 */
	public static String toHexString(byte a[]) {
		if (a == null)
			return null;
		char sa[] = new char[a.length * 2];
		for (int i = 0; i < a.length; i++) {
			int x = (a[i] >> 4) & 0xf;
			sa[i * 2] = (char) (x < 10 ? '0' + x : 'A' + x - 10);
			x = a[i] & 0xf;
			sa[i * 2 + 1] = (char) (x < 10 ? '0' + x : 'A' + x - 10);
			// String s = Integer.toHexString((a[i]&0xff)+0x100);
			// s = s.substring(s.length()-2).toUpperCase();
			// System.out.print(s);
		}
		return new String(sa);
	}

	public static String hexEncode(byte a[]) {
		return toHexString(a);
	}

	public static byte[] hexDecode(String s) {
		int n = s.length();
		final byte a[] = new byte[(n + 1) >> 1];
		int j = a.length - 1;
		for (int i = n - 1; i >= 0; i -= 2) {
			final int x0, x1;
			{
				final char c0 = s.charAt(i);
				if (c0 >= 'a' && c0 <= 'f')// c0 = (char)(c0+'A' - 'a');
					x0 = c0 + 10 - 'a';
				else if (c0 >= 'A' && c0 <= 'F')// c0 = (char)(c0+'A' - 'a');
					x0 = c0 + 10 - 'A';
				else if (c0 >= '0' && c0 <= '9')
					x0 = c0 - '0';
				else
					throw new java.lang.NumberFormatException(s);
			}
			if (i < 1)
				x1 = 0;
			else {
				final char c1 = s.charAt(i - 1);// : '0'; //if( c1>='a' &&
												// c1<='z' ) c1 = (char)(c1+'A'
												// - 'a');
				if (c1 >= 'a' && c1 <= 'f')// c0 = (char)(c0+'A' - 'a');
					x1 = c1 + 10 - 'a';
				else if (c1 >= 'A' && c1 <= 'F')// c0 = (char)(c0+'A' - 'a');
					x1 = c1 + 10 - 'A';
				else if (c1 >= '0' && c1 <= '9')
					x1 = c1 - '0';
				else
					throw new java.lang.NumberFormatException(s);
			}
			assert (x0 >= 0 && x0 <= 0xf && x1 >= 0 && x1 <= 0xf);
			a[j--] = (byte) ((x1 << 4) | x0);
		}
		assert (j == -1);
		return a;
	}

	
	
	/**
	 * 比较两个数组的值，根据不同情况返回不同的值： 逐个元素比较两个数组中元素的大小，直到有不等的元素或比较完成；
	 * 比较的方式按照Comparable借口进行。 示例1：StrUtil.compare(new String[] {"a","b","c"},
	 * new String[] {"a","b","c"}); 结果：0 示例2：StrUtil.compare(new String[]
	 * {"a","b","c"}, new String[] {"a","b","d"}); 结果：-1 示例3：StrUtil.compare(new
	 * String[] {"a","b","c"}, new String[] {"a","b","b"}); 结果：1
	 * 
	 * @param a1
	 *            进行比较的数组a1
	 * @param a2
	 *            进行比较的数组a2
	 * @return 0： a1和a2相等 1： a1大于a2 -1： a1小于a2
	 */
	static public int compare(final String a1[], final String a2[]) {
		if (a1 == a2)
			return 0;
		final int n1 = a1 == null ? 0 : a1.length;
		final int n2 = a2 == null ? 0 : a2.length;
		for (int i = 0; i < n1 || i < n2; i++) {
			String name1 = i < n1 ? a1[i] : null;
			String name2 = i < n2 ? a2[i] : null;
			if (name1 == name2)
				continue;
			if (name1 == null)
				return -1;
			if (name2 == null)
				return 1;
			int k = name1.compareTo(name2);
			if (k != 0)
				return k;
		}
		return 0;
	}

	/*
	 * GB2312汉字拼音对照表:
	 * http://wiki.w3china.org/wiki/index.php/GB2312%E6%B1%89%E5%AD
	 * %97%E6%8B%BC%E9%9F%B3%E5%AF%B9%E7%85%A7%E8%A1%A8
	 */
	//
	//
	//
	final static int ChnPY1[] = { 0xb0a1, // a
			0xb0c5, // b
			0xb2c1, // c
			0xb4ee, // d
			0xb6ea, // e
			0xb7a2, // f
			0xb8c1, // g
			0xb9fe, // g
			0xbbf7, // i :
			0xbbf7, // j
			0xbfa6, // k
			0xc0ac, // l
			0xc2e8, // m
			0xc4c3, // n
			0xc5b6, // o
			0xc5be, // p
			0xc6da, // q
			0xc8bb, // r
			0xc8f6, // s
			0xcbfa, // t
			0xcdda, // - // u
			0xcdda, // v
			0xcdda, // w
			0xcef4, 0xd1b9,// d4d0
			0xd4d1,// d7f9 // z
			0xd7fa };
	/*
	 * 二级汉字 D8A1 D8FE F7A1 F7FE 32 * 94 = 3008
	 */
	static byte Pinying2[];

	static char getGbcodePinying2(int gbCode1, int gbCode2) {
		if (Pinying2 == null) {
			InputStream in = null;// StrUtil.class.getResourceAsStream("Pinying2.txt");
			try {
				in = StrUtil.class.getResourceAsStream("Pinying2.txt");
				Pinying2 = new byte[32 * 94];
				for (int i = 0; i < 32; i++) {
					for (int j = 0; j < 94; j++) {
						Pinying2[i * 94 + j] = (byte) in.read();
					}
					for (;;) {
						int c = in.read();
						if (c < 0 || c == '\n')
							break;
					}
				}
			} catch (Throwable ex) {
				Pinying2 = null;
				ex.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (Throwable ex) {
					}
			}
			if (Pinying2 == null) {
				Pinying2 = new byte[0];
			}
		}
		if (Pinying2.length == 0)
			return ' ';
		return (char) Pinying2[(gbCode1 - 0xd8) * 94 + (gbCode2 - 0xa1)];
	}

	

	/**
	 * 返回汉字字符串的首字母缩写组合。 编码方式GB2312。
	 * 
	 * @param text
	 * @param orBits
	 * @param mode
	 * @return
	 */
	public static String toChnPinying2(String text, int orBits, int mode) {
		if (text == null)
			return null;
		// a[] = text.toCharArray();
		// int ltext = text.length();
		byte a[] = text.getBytes();
		StringBuilder sb = new StringBuilder();
		final int A = mode == 2 ? 'A' : 'a';
		for (int i = 0; i < a.length; i++) {
			byte c = a[i];
			if (i == a.length - 1 || (c & 0xff) < 0xa0) {
				if (mode == 1 && c >= 'A' && c <= 'Z')
					c += 'a' - 'A';
				else if (mode == 1 && c >= 'A' && c <= 'Z')
					c += 'A' - 'a';
				sb.append((char) c);
				continue;
			}
			int gbc1, gbc2;
			int x = ((gbc1 = c & 0xff) << 8) | (gbc2 = a[i + 1] & 0xff); // GB
			i++; // ((b[0]&0xff)<<8)|(b[1]&0xff);
			int j = java.util.Arrays.binarySearch(ChnPY1, x);
			if (j >= 0) {
				for (; j < ChnPY1.length - 1 && ChnPY1[j + 1] == x; j++)
					;
			} else {
				j = -j - 2;
			}
			if (j >= 0 && j < 26) {
				sb.append((char) ((A + j) | orBits));
			} else if (x == 0xA1A1) {
				sb.append((char) (' ' | orBits));
				// sb.append((char)a[i]);
			} else
			// 二级汉字
			{
				if (gbc1 >= 0xd8 && gbc1 <= 0xf7 && gbc2 >= 0xa1
						&& gbc2 <= 0xfe)
					sb.append((char) (getGbcodePinying2(gbc1, gbc2) | orBits));
			}
		}
		return sb.toString();
		// return changed ? sb.toString() : text;
	}

	/**
	 * @param text
	 * @param mode
	 * @return
	 */
	public static String toChncodeString(String text, int mode) {
		if (text == null)
			return null;
		byte a[];
		try {
			a = text.getBytes("GB18030");
		} catch (java.io.UnsupportedEncodingException ex) {
			a = text.getBytes();
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			byte c = a[i];
			if (i == a.length - 1 || (c & 0xff) < 0xa0) {
				if (mode == 1 && c >= 'A' && c <= 'Z')
					c += 'a' - 'A';
				else if (mode == 1 && c >= 'A' && c <= 'Z')
					c += 'A' - 'a';
				sb.append((char) c);
				continue;
			}
			int x = ((c & 0xff) << 8) | (a[i + 1] & 0xff);
			i++; // ((b[0]&0xff)<<8)|(b[1]&0xff);
			sb.append((char) x);
		}
		return sb.toString();
	}

	/**
	 * 读取字符流中的数据，将数据转换为字符串并返回
	 * 
	 * @param reader
	 *            字符流
	 * @return 读取数据组装成的字符串
	 * @throws IOException
	 */
	static public String toString(java.io.Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		char buffer[] = new char[16 * 1024];
		for (;;) {
			int count = reader.read(buffer);
			if (count < 0)
				break;
			// if( count<buffer.length && sb.length()==0 )
			// return new String(buffer,0,count);
			sb.append(buffer, 0, count);
		}
		return sb.toString();
	}

	/**
	 * 将Object数组转换为字符串数组,转换过程中是循环调用各个对象的toString()方法 建议：实现Object对象的toString()方法
	 * 
	 * @param a
	 *            Object数组
	 * @return String数组
	 */
	static public String[] toStringArray(Object a[]) {
		if (a == null)
			return null;
		final int n = a.length;
		String sa[] = new String[n];
		for (int i = 0; i < n; i++)
			sa[i] = a[i] == null ? null : a[i].toString();
		return sa;
	}

	

	/**
	 * 查找字符串s在字符串数组中的位置，字符串比较不区分大小写，如果不存在则返回-1 示例：String[] s2 = new String[] {
	 * "a", "b",null,"d" }; 1、StrUtil.indexOfIgnoreCase(s2,"b"); 结果：1
	 * 2、StrUtil.indexOfIgnoreCase(s2,"c"); 结果：-1
	 * 3、StrUtil.indexOfIgnoreCase(s2,"B"); 结果：1
	 * 
	 * @param a
	 *            查找的目标数组
	 * @param s
	 *            要查找的字符串
	 * @return 字符串s在数组a中的位置
	 */
	static public int indexOfIgnoreCase(String a[], String s) {
		if (a == null)
			return -1;
		for (int i = 0; i < a.length; i++) {
			if (s == a[i] || (s != null && s.equalsIgnoreCase(a[i])))
				return i;
		}
		return -1;
	}

	/**
	 * 搜索字符串以字符串数组中哪个字符串元素开头，返回该字符串在数组中下标 示例：StrUtil.startsWith("eeyaya", new
	 * String[]{"aa","bb","dd","ee","ff"}) 结果：3
	 * 
	 * @param s
	 *            要搜索的字符串
	 * @param prefix
	 *            字符串数组
	 * @return 字符串数组中是s开头的第一个字符串的下标
	 */
	static public int startsWith(String s, String prefix[]) {
		if (s == null || prefix == null)
			return -1;
		for (int i = 0; i < prefix.length; i++) {
			if (s.startsWith(prefix[i]))
				return i;
		}
		return -1;
	}

	/**
	 * 替换字符串中指定字符串 示例：StrUtil.replace("aabbcc","bb","dd"); 结果：aaddcc
	 * 
	 * @param text
	 *            字符串
	 * @param subtext
	 *            text原来存在的字符串
	 * @param replace
	 *            将要替换的新字符串
	 * @return 替换后的新字符串
	 */
	static public String replace(String text, String subtext, String replace) {
		if (text == null || subtext == null || subtext.length() == 0
				|| subtext.equals(replace))
			return text;
		int p = text.indexOf(subtext);
		if (p < 0)
			return text;
		StringBuilder sb = new StringBuilder();
		for (; p >= 0;) {
			sb.append(text.substring(0, p));
			if (replace != null)
				sb.append(replace);
			text = text.substring(p + subtext.length());
			p = text.indexOf(subtext);
		}
		sb.append(text);
		return sb.toString();
	}

	/**
	 * 从源字节数据的指定索引处查找目标字节数组的索引， 如果可能找到，则返回该索引；其他一概返回-1.
	 * 
	 * @param a源字节数据
	 * @param from
	 *            开始索引
	 * @param b
	 *            目标字节数组
	 * @return
	 */
	static public int indexOf(byte a[], int from, byte b[]) {
		if (a == null || b == null)
			return -1;
		if (b.length == 0)
			return 0;
		int n = a.length - b.length;
		int nb = b.length;
		nextI: for (int i = from; i < n; i++) {
			if (a[i] == b[0]) {
				for (int j = 1; j < nb; j++)
					if (a[i + j] != b[j])
						continue nextI;
				return i;
			}
		}
		return -1;
	}

	/**
	 * 判断byte数组a从下标from元素开始是否包含byte数组b的所有元素，且元素顺利完全相同 示例： byte[] a= new byte[]
	 * {1,2,3,4,5,6,7,8,9,10}; byte[] b= new byte[] {6,7,8,9};
	 * System.out.println(StrUtil.startsWith(a, 5, b));
	 * 
	 * @param a
	 *            byte数组
	 * @param from
	 *            开始进行判断的byte数组下标
	 * @param b
	 *            byte数组
	 * @return 数组a的后半部分数据与数组b是否完全相同
	 */
	static public boolean startsWith(byte a[], int from, byte b[]) {
		final int nb = b.length;
		for (int i = 0; i < nb; i++) {
			final int j = from + i;
			if (j >= a.length || a[j] != b[i])
				return false;
		}
		return true;
	}

	static final public char HexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * options : bit 1 : replaceStr 2 : "" for null macro 4 ： 字符串外 含空的宏时返回 null
	 * 8 : 字符串内 含空的宏时返回 null 16 : "0" for null macro 32 : 禁止参数型的 宏( XXX$2 不认为代
	 * 参数 类型 的宏 ) 64: '.' 不能含在 宏名中 128 : 宏前面的 ＃ 处理为 字符串 "..."
	 */
	/**
	 * 将集合Map中指定name的值转换为int整数类型
	 * 
	 * @param m
	 *            包含转换对象的集合
	 * @param name
	 *            集合中要进行转换的值对应的key
	 * @return 转换成的整数
	 */
	static public final int obj2int(java.util.Map m, String name) {
		return obj2int(m.get(name));
	}

	/**
	 * 判断传入字符串首字母是否为大写字母，如果为小写字母则转换为对应的大写字母并返回 如果是大写字母将原字符串返回
	 * 示例1：StrUtil.toUpper1("abc"); 结果：Abc 示例2：StrUtil.toUpper1("Abc"); 结果：Abc
	 * 
	 * @param text
	 *            进行转换的字符串
	 * @return 转换完成的字符串
	 */
	final static public String toUpper1(String text) {
		if (text == null || text.length() == 0)
			return text;
		char c = text.charAt(0);
		if (c >= 'a' && c <= 'z')
			return Character.toString((char) (c + 'A' - 'a'))
					+ text.substring(1);
		return text;
	}

	/**
	 * 用于生成一个唯一标识符 (UUID) 示例： StrUtil.newUIID();
	 * 结果：6D816257142F4D46AB74404C73A4A3D4 每次执行都能生成不重复的值
	 * 
	 * @return 唯一标识符 (UUID)
	 */
	final static public String newUIID() {
		java.util.UUID uiid = java.util.UUID.randomUUID();
		long mostSigBits = uiid.getMostSignificantBits();
		long leastSigBits = uiid.getLeastSignificantBits();
		return (digits(mostSigBits >> 32, 8) + // "-" +
				digits(mostSigBits >> 16, 4) + // "-" +
				digits(mostSigBits, 4) + // "-" +
				digits(leastSigBits >> 48, 4) + // "-" +
		digits(leastSigBits, 12)).toUpperCase();
	}

	/** Returns val represented by the specified number of hex digits. */
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);// .toUpperCase();
	}

	/**
	 * 删除字符串中以startsWith开头的行，其它行组成新的字符串返回 示例：
	 * StrUtil.removeStartsWithLine("aaa123\nbbb456\nccc789"
	 * ,"aaa");结果：bbb456\nccc789
	 * 
	 * @param text
	 *            原字符串
	 * @param startsWith
	 *            要删除行的前缀
	 * @return 新的字符串
	 */
	final public static String removeStartsWithLine(String text,
			String startsWith) {
		if (text == null)
			return null;
		boolean hasRemoved = false;
		StringBuilder sb = new StringBuilder();
		final int n = text.length();
		int i0 = 0;
		for (; i0 < n;) {
			for (; i0 < n && text.charAt(i0) == ' '; i0++)
				;
			int p = text.indexOf('\n', i0);
			if (p < 0)
				p = n;
			if (!text.startsWith(startsWith, i0)) {
				// if( sb.length()>0 )
				// sb.append("\r\n");
				sb.append(text.substring(i0, p < n ? p + 1 : n));
				// hasRemoved = true;
			} else {
				hasRemoved = true;
			}
			i0 = p + 1;
		}
		// for()
		return hasRemoved ? sb.toString().trim() : text.trim();
		// if( s.startsWith(prefix))
		// cjmethod:
	}
	
	public static boolean isnull(String value){
		return null==value||value.length()<=0;
	}
	
	public static boolean isnull2(String value){
		return null==value || value.length()<=0 || "null".equals(value);
	}
	
	/**
	 * 忽略大小写判断 判断 str 是否是 前缀 prefix
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}

		final int len = prefix.length();
		if (str.length() < len) {
			return false;
		}

		return str.regionMatches(true, 0, prefix, 0, len);
	}

	public static boolean containsIgnoreCase(String source, String target)

	{
		final int sourceCount = source.length();
		final int targetCount = target.length();
		if (targetCount == 0)
			return true;
		char first = target.charAt(0);// [targetOffset];
		if (first >= 'A' && first <= 'Z')
			first = (char) (first + 'a' - 'A');
		final int max = sourceCount - targetCount;// sourceOffset + (sourceCount
													// - targetCount);

		nextI: for (int i = 0; i <= max; i++) {
			{
				/* Look for first character. */
				final char c = source.charAt(i);
				if (((c >= 'A' && c <= 'Z') ? c + 'a' - 'A' : c) != first)
					continue;
			}
			for (int j = 1; j < targetCount; j++) {
				char c1 = source.charAt(i + j);
				char c2 = target.charAt(j);
				if (((c1 >= 'A' && c1 <= 'Z') ? c1 + 'a' - 'A' : c1) != ((c2 >= 'A' && c2 <= 'Z') ? c2 + 'a' - 'A'
						: c2))
					continue nextI;
			}
			return true;
		}
		return false;
	}

	public static String[] subArray(String a[], int from) {
		if (from == 0)
			return a;
		String newa[] = new String[a.length - from];
		System.arraycopy(a, from, newa, 0, newa.length);
		return newa;
	}

	static public int indexOf(char a[], char c) {
		if (a == null)
			return -1;
		for (int i = 0; i < a.length; i++) {
			if (c == a[i])
				return i;
		}
		return -1;
	}

	static void inc(char a[], int p, char sets[]) {
		if (p < 0)
			throw new RuntimeException();
		final char c = a[p];
		final int j = indexOf(sets, c);
		if (j < sets.length - 1)
			a[p] = sets[j + 1];
		else {
			a[p] = sets[0];
			inc(a, p - 1, sets);
		}
	}

	public static String inc(String text, char sets[]) {
		char a[] = text.toCharArray();
		inc(a, a.length - 1, sets);
		return new String(a);
	}

	/**
	 * 取指定UTF8编码长度的值
	 * 
	 * @param str
	 * @param maxUtf8Len
	 * @return
	 */
	static String trimUTF8(String str, int maxUtf8Len) {
		int strlen = str.length();
		int utflen = 0;
		for (int i = 0; i < strlen; i++) {
			char c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				utflen++;
			} else if (c > 0x07FF) {
				utflen += 3;
			} else {
				utflen += 2;
			}
			if (utflen > maxUtf8Len) {
				return str.substring(0, i);
			}
		}
		return str;
	}
	
	/**
	 * 匹配字符串
	 * @param strList
	 * @param s
	 * @return
	 */
	static public boolean isStrIn(String strList, String s) {
		return (("," + strList + ",").indexOf("," + s + ",") >= 0);
	}

	static public boolean isStrIn(String strList, String s, char c) {
		return ((c + strList + c).indexOf(c + s + c) >= 0);
	}

	/**
	 * @功能:
	 * @作者:周雷
	 * @时间:2015年12月9日 下午6:41:04
	 * @param strList
	 * @param s
	 * @param c
	 * @return
	 */
	static public boolean isStrIn(String strList, String s, String c) {
		return ((c + strList + c).indexOf(c + s + c) >= 0);
	}

	static public boolean isIntIn(String strList, int i) {
		return (("," + strList + ",").indexOf("," + i + ",") >= 0);
	}
	
	static public String subStrAfter(String str, char c) {
		return subStrAfter(str, c + "");
	}

	static public String subStrAfter(String str, String disStr) {
		if (str == null) {
			return null;
		}
		int idx = str.indexOf(disStr);
		if (idx < 0)
			return str;
		return str.substring(idx + disStr.length());
	}

	static public String subStrBefore(String str, char c) {
		return subStrBefore(str, c + "");
	}

	static public String subStrBefore(String str, String disStr) {
		if (str == null) {
			return null;
		}
		int idx = str.indexOf(disStr);
		if (idx < 0)
			return str;
		return str.substring(0, idx);
	}
	
	/**
	 * @功能:字符串转Set
	 * @作者:周雷
	 * @时间:2015年12月9日 上午10:18:58
	 * @param strList 自负串
	 * @param regex 分隔符
	 * @return
	 */
	public static Set<String> asSet(String strList,String regex){
		Set<String> set = new HashSet<String>();
		if(null==strList||strList.isEmpty()){
			return  null;
		}else{
			String[] strLists = strList.split(regex);
			for (String str : strLists) {
				set.add(str);
			}
		}
		return set;
	}
	
	/**
	 * @功能:字符串转List
	 * @作者:周雷
	 * @时间:2015年12月9日 上午10:18:58
	 * @param strList 自负串
	 * @param regex 分隔符
	 * @return
	 */
	public static List<String> asList(String strList,String regex){
		List<String> list = new ArrayList<String>();
		if(null==strList||strList.isEmpty()){
			return  null;
		}else{
			String[] strLists = strList.split(regex);
			for (String str : strLists) {
				list.add(str);
			}
		}
		return list;
	}
	
	/**
	 * @功能:s是否全在strList中
	 * @作者:周雷
	 * @时间:2015年12月9日 下午6:08:16
	 * @param strList 匹配的regex分割的字符串
	 * @param s 被匹配的regex分割的字符串
	 * @param regex
	 * @return
	 */
	public static boolean isallin(String strList, String s, String regex){
		if (isnull(strList)&& isnull(s))
			return true;
		if (isnull(strList)|| isnull(s))
			return false;
		Set<String> strListSet = asSet(strList, regex);
		String[] ss = s.split(regex);
		for (String str : ss) {
			if(!strListSet.contains(str)){
				return false;
			}
		}
		return true;
	}
	/**
	 * @功能:s是否有一个在strList中存在
	 * @作者:周雷
	 * @时间:2015年12月9日 下午6:08:16
	 * @param strList 匹配的regex分割的字符串
	 * @param s 被匹配的regex分割的字符串
	 * @param regex
	 * @return
	 */
	public static boolean isExistOne(String strList, String s, String regex){
		if (isnull(strList)&& isnull(s))
			return true;
		if (isnull(strList)|| isnull(s))
			return false;
		String[] categories = s.split(",");
		for (String str : categories) {
			if(isStrIn(strList, str, regex)){
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean isExistOne(T[] o,Set<T> s){
		if(null==o||null==s)return false;
		for (Object ob : o) {
			if(s.contains(ob)){
				return true;
			}
		}
		return false;
	}
	public static <T> boolean isExistAllInArray(T[] o,Set<T> s){
		if(null==o||null==s)return false;
		int k = 0;
		for (Object ob : o) {
			if(s.contains(ob)){
				k++;
			}
		}
		return s.size()==k&&k!=0;
	}
	public static <T> boolean isExistAllInSet(Set<T> s,T[] o){
		if(null==o||null==s)return false;
		int k = 0;
		for (Object ob : o) {
			if(s.contains(ob)){
				k++;
			}
		}
		return o.length==k&&k!=0;
	}
	
	/**
	 * @功能:生成随机数字字符
	 * @作者:周雷
	 * @时间:2016年1月13日 下午3:32:03
	 * @param n
	 * @return
	 */
	private static Random random = new Random();
	public static String randomStr(int n){
		String rm = "";
		for (int i = 0; i < n; i++) {
			rm = rm + random.nextInt(10);
		}
		return rm;
	}
	
	public static int[] getPageLimit(String page,String limit,int limitDefault){
		int l = StrUtil.obj2int(limit, limitDefault);
		int p = StrUtil.obj2int(page, 0);
		p = p>0?p-1:Math.max(p, 0);
		p = p*l;
		int[] pag = {p,l};
		return pag;
	}
	
	public static PageBounds getPages(int page,int limit){
		return new PageBounds(Math.max(page, 0), Math.max(limit, 1));
	}
	
	public static List<List<?>> splitList(List<?> list, int len) {  
		if (list == null || list.size() == 0 || len < 1) {  
		return null;  
		}  
		  
		List<List<?>> result = new ArrayList<List<?>>();  
		  
		  
		int size = list.size();  
		int count = (size + len - 1) / len;  
		  
		  
		for (int i = 0; i < count; i++) {  
		List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));  
		result.add(subList);  
		}  
		return result;  
		}  
}
