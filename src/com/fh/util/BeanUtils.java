package com.fh.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

@SuppressWarnings("unchecked")
public class BeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
	/**
	 * Administrator_guojf
	 * Dec 30, 2008 12:21:35 AM
	 * @param args
	 * TODO:
	 */



	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}");
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}");
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}
	
	public static String getMethodName(String fildeName) throws Exception{  
		byte[] items = fildeName.getBytes();  
		items[0] = (byte) ((char) items[0] - 'a' + 'A');  
		return new String(items);  
	}
	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}
	
	public static Iterator CopyIterator(Class classType, Iterator src) {
		return CopyList(classType, src).iterator();
	}

	public static List CopyList(Class classType, Iterator src) {
		List tag = new ArrayList();
		while (src.hasNext()) {
			Object obj = null, ormObj = src.next();
			try {
				obj = classType.newInstance();
				BeanWrapper wrapper = new BeanWrapperImpl(obj);
				BeanWrapper wrapper1 = new BeanWrapperImpl(ormObj);
				PropertyDescriptor descriptors[] = wrapper
						.getPropertyDescriptors();
				for (int i = 0; i < descriptors.length; i++) {
					String name = descriptors[i].getName();
					wrapper.setPropertyValue(name, wrapper1
							.getPropertyValue(name));
				}

			} catch (Exception e) {
			}
			if (obj != null)
				tag.add(obj);
		}
		return tag;
	}

	
	public static void Map2Obj(Map map, Object obj) {
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			Object v = map.get(name);
			if (v != null && descriptors[i].getWriteMethod() != null) {
				wrapper.setPropertyValue(name, v);
			}
		}
	}
	
	public static void Obj2Map(Object obj, Map map) {
		if (map == null) map = new java.util.HashMap();
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			try {
				if (descriptors[i].getReadMethod() != null) {
					map.put(name, wrapper.getPropertyValue(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Map Obj2Map(Object obj) {
		Map map = new java.util.HashMap();
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			try {
				if (descriptors[i].getReadMethod() != null) {
					map.put(name, wrapper.getPropertyValue(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	@SuppressWarnings("unused")
	public static String[] getMapKeyValue(Map map){
	       Iterator ite = map.entrySet().iterator();
	        while (ite.hasNext())
	        {
	            Map.Entry obj = (Map.Entry) ite.next();
				Object key = obj.getKey();
	            Object value = obj.getValue();
	        }
			return null;
	}
	
//===================================================================
	  /**
	   * Converts a serializable object to a byte array.
	   */
	  public static byte[] objectToBytes(Object object) throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(baos);
	    os.writeObject(object);
	    return baos.toByteArray();
	  }

	  /**
	   * Converts a byte array to a serializable object.
	   */
	  public static Object bytesToObject(byte[] bytes) throws IOException,
	      ClassNotFoundException {
	    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	    ObjectInputStream is = new ObjectInputStream(bais);
	    return is.readObject();
	  }
//=========================================================================

		/**
		 * Instantaite an Object from a given class name
		 * 
		 * @param className
		 *            full qualified name of the class
		 * @return the instantaited Object
		 * @exception java.lang.Exception
		 *                if instantiation failed
		 */
		public static Object createObject(String className) throws Exception {
			return createObject(Class.forName(className));
		}

		/**
		 * Instantaite an Object instance
		 * 
		 * @param classObject
		 *            Class object representing the object type to be instantiated
		 * @return the instantaied Object
		 * @exception java.lang.Exception
		 *                if instantiation failed
		 */
		public static Object createObject(Class classObject) throws Exception {
			return classObject.newInstance();
		}

		/**
		 * Instantaite an Object instance, requires a constructor with parameters
		 * 
		 * @param className
		 *            full qualified name of the class
		 * @param params
		 *            an array including the required parameters to instantaite the
		 *            object
		 * @return the instantaited Object
		 * @exception java.lang.Exception
		 *                if instantiation failed
		 */
		public static Object createObject(String className, Object[] params) throws Exception {
			return createObject(Class.forName(className), params);
		}

		/**
		 * Instantaite an Object instance, requires a constractor with parameters
		 * 
		 * @param classObject
		 *            , Class object representing the object type to be instantiated
		 * @param params
		 *            an array including the required parameters to instantaite the
		 *            object
		 * @return the instantaied Object
		 * @exception java.lang.Exception
		 *                if instantiation failed
		 */
		public static Object createObject(Class classObject, Object[] params) throws Exception {
			Constructor[] constructors = classObject.getConstructors();
			Object object = null;
			for (int counter = 0; counter < constructors.length; counter++) {
				try {
					object = constructors[counter].newInstance(params);
				} catch (Exception e) {
					if (e instanceof InvocationTargetException)
						((InvocationTargetException) e).getTargetException().printStackTrace();
					// do nothing, try the next constructor
				}
			}
			if (object == null)
				throw new InstantiationException();
			return object;
		}

		public static Class createClass(String className) {
			Class classService = null;
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				classService = classLoader.loadClass(className);
			} catch (Exception ex) {
				System.err.print("[JdonFramework] createClass error:" + ex);
			}
			return classService;
		}
//==========================================================================================


		public static Class[] getParentAllInterfaces(Class pojoClass) {
			Class[] interfaces = null;
			try {
				List interfacesL = new ArrayList();
				while (pojoClass != null) {
					for (int i = 0; i < pojoClass.getInterfaces().length; i++) {
						Class ifc = pojoClass.getInterfaces()[i];
						// not add jdk interface
						if (!ifc.getName().startsWith("java."))
							interfacesL.add(ifc);
					}
					pojoClass = pojoClass.getSuperclass();
				}
				if (interfacesL.size() == 0) {
					throw new Exception();
				}
				interfaces = (Class[]) interfacesL.toArray(new Class[interfacesL.size()]);
			} catch (Exception e) {
			}
			return interfaces;
		}

		public static Class[] getAllInterfaces(Class clazz) {
			if (clazz == null) {
				return new Class[0];
			}
			List<Class> classList = new ArrayList<Class>();
			while (clazz != null) {
				Class[] interfaces = clazz.getInterfaces();
				for (Class interf : interfaces) {
					if (!classList.contains(interf)) {
						classList.add(interf);
					}
					Class[] superInterfaces = getAllInterfaces(interf);
					for (Class superIntf : superInterfaces) {
						if (!classList.contains(superIntf)) {
							classList.add(superIntf);
						}
					}
				}
				clazz = clazz.getSuperclass();
			}
			return classList.toArray(new Class[classList.size()]);
		}

		public static Field[] getAllDecaredFields(Class clazz) {
			List<Field> fields = new ArrayList<Field>();
			// fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

			Class[] superClasses = getAllSuperclasses(clazz);
			for (Class superClass : superClasses) {
				fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
			}
			return fields.toArray(new Field[fields.size()]);
		}

		public static Class[] getAllSuperclasses(Class cls) {
			if (cls == null) {
				return new Class[0];
			}
			List<Class> classList = new ArrayList<Class>();
			Class superClass = cls;
			while (superClass != null && !Object.class.equals(superClass) && !Class.class.equals(superClass)) {
				classList.add(superClass);
				superClass = superClass.getSuperclass();
			}
			return classList.toArray(new Class[classList.size()]);
		}

		public static Field getDecaredField(Class clazz, String name) throws NoSuchFieldException {
			Field field = null;
			Class[] superClasses = getAllSuperclasses(clazz);
			for (Class superClass : superClasses) {
				try {
					field = superClass.getDeclaredField(name);
					break;
				} catch (NoSuchFieldException e) {
					// ignore
				}
			}
			if (field == null) {
				throw new NoSuchFieldException("No such declared field " + name + " in " + clazz);
			}
			return field;
		}


    public static final boolean toBoolean(final Object value, final boolean defaultValue) {
            final Boolean v = toBoolean(value);
            return v == null ? defaultValue : v.booleanValue();
    }

    public static final Boolean toBoolean(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Boolean) {
                    return (Boolean) obj;
            } else if (obj instanceof Number) {
                    return ((Number) obj).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
            } else if (obj instanceof String) {
                    final String s = (String) obj;
                    if (s.equalsIgnoreCase("true")) {
                            return Boolean.TRUE;
                    } else if (s.equalsIgnoreCase("false")) {
                            return Boolean.FALSE;
                    } else {
                            try {
                                    return new Boolean(Integer.parseInt((String) obj) != 0);
                            } catch (final Throwable t) {
                                    return Boolean.FALSE;
                            }
                    }
            }
            return null;
    }

    public static final Byte toByte(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Number) {
                    return new Byte(((Number) obj).byteValue());
            } else if (obj instanceof Boolean) {
                    return obj.equals(Boolean.FALSE) ? new Byte((byte) 0) : new Byte((byte) -1);
            } else {
                    try {
                            return Byte.valueOf(obj.toString());
                    } catch (final Throwable t) {
                    }
            }
            return null;
    }

    public static final byte toByte(final Object obj, final byte defaultValue) {
            final Byte s = toByte(obj);
            return s == null ? defaultValue : s.byteValue();
    }

    public static final Short toShort(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Number) {
                    return new Short(((Number) obj).shortValue());
            } else if (obj instanceof Boolean) {
                    return obj.equals(Boolean.FALSE) ? new Short((short) 0) : new Short((short) -1);
            } else {
                    try {
                            return Short.valueOf(obj.toString());
                    } catch (final Throwable t) {
                    }
            }
            return null;
    }

    public static final short toShort(final Object obj, final short defaultValue) {
            final Short s = toShort(obj);
            return s == null ? defaultValue : s.shortValue();
    }

    public static final Integer toInt(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Number) {
                    return new Integer(((Number) obj).intValue());
            } else if (obj instanceof Boolean) {
                    return obj.equals(Boolean.FALSE) ? new Integer(0) : new Integer(-1);
            } else {
                    try {
                            return Integer.valueOf(obj.toString());
                    } catch (final Throwable t) {
                    }
            }
            return null;
    }

    public static final int toInt(final Object obj, final int defaultValue) {
            final Integer i = toInt(obj);
            return i == null ? defaultValue : i.intValue();
    }

    public static final Long toLong(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Long) {
                    return (Long) obj;
            } else if (obj instanceof Number) {
                    return new Long(((Number) obj).longValue());
            } else if (obj instanceof Date) {
                    return new Long(((Date) obj).getTime());
            } else if (obj instanceof java.sql.Timestamp) {
                    return new Long(((java.sql.Timestamp) obj).getTime());
            } else {
                    try {
                            return new Long(Long.parseLong(obj.toString()));
                    } catch (final Throwable t) {
                    }
            }
            return null;
    }

    public static final long toLong(final Object obj, final long defaultValue) {
            final Long l = toLong(obj);
            return l == null ? defaultValue : l.longValue();
    }

    public static final Double toDouble(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof Number) {
                    return new Double(((Number) obj).doubleValue());
            } else if (obj instanceof Boolean) {
                    return obj.equals(Boolean.FALSE) ? new Double(0.0) : new Double(-1.0);
            } else {
                    try {
                            return Double.valueOf(obj.toString());
                    } catch (final Throwable t) {
                    }
            }
            return null;
    }

    public static final double toDouble(final Object obj, final double defaultValue) {
            final Double d = toDouble(obj);
            return d == null ? defaultValue : d.doubleValue();
    }

    public static final String toString(final Object obj) {
            if (obj == null) {
                    return null;
            } else if (obj instanceof String) {
                    return (String) obj;
            } else if (obj instanceof Throwable) {
                    final StringWriter writer = new StringWriter();
                    ((Throwable) obj).printStackTrace(new PrintWriter(writer));
                    return writer.toString();
            } else if (obj instanceof char[]) {
                    return String.valueOf((char[]) obj);
            } else {
                    return String.valueOf(obj);
            }
    }

    public static final String toString(final Object obj, final String defaultValue) {
            final String s = toString(obj);
            return s == null ? defaultValue : s;
    }

    public static String defaultDatePattern = "yyyy-MM-dd HH:mm";

    public static final String toDateString(final Date date, final String pattern) {
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return date == null ? null : sdf.format(date);
    }

    public static final String toDateString(final Date date) {
            return toDateString(date, defaultDatePattern);
    }

    public static final Date toDate(final String dateString, final String pattern) {
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                    return sdf.parse(dateString);
            } catch (final Exception e) {
                    return null;
            }
    }

    public static <T extends Enum<T>> T toEnum(final Class<T> enumClazz, final Object obj) {
            try {
                    final int index = toInt(obj, -1);
                    if (index > -1) {
                            final T[] arr = enumClazz.getEnumConstants();
                            return arr[index];
                    } else {
                            return Enum.valueOf(enumClazz, toString(obj));
                    }
            } catch (final Throwable e) {
                    return enumClazz.getEnumConstants()[0];
            }
    }

}
