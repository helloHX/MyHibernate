package com.hwl.hibernate.cfg;

import com.hwl.hibernate.cfg.jaxb.JaxbCfgMappingReferenceType;

/**
 * class MappingReference
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ8ÈÕ
 */
public class MappingReference {
	public static enum Type {
		RESOURCE, CLASS, FILE, JAR, PACKAGE
	}

	private final Type type;
	private final String reference;

	public MappingReference(Type type, String reference) {
		this.type = type;
		this.reference = reference;
	}

	public Type getType() {
		return type;
	}

	public String getReference() {
		return reference;
	}

	public static MappingReference consume(JaxbCfgMappingReferenceType jaxbMapping) {
		if (jaxbMapping.getClazz() != null && !"".equals(jaxbMapping.getClazz())) {
			return new MappingReference(MappingReference.Type.CLASS, jaxbMapping.getClazz());
		}
		if (jaxbMapping.getFile() != null && !"".equals(jaxbMapping.getFile())) {
			return new MappingReference(MappingReference.Type.FILE, jaxbMapping.getFile());
		}
		if (jaxbMapping.getResource() != null && !"".equals(jaxbMapping.getResource())) {
			return new MappingReference(MappingReference.Type.RESOURCE, jaxbMapping.getResource());
		}
		if (jaxbMapping.getJar() != null && !"".equals(jaxbMapping.getJar())) {
			return new MappingReference(MappingReference.Type.JAR, jaxbMapping.getJar());
		}
		if (jaxbMapping.get_package() != null && !"".equals(jaxbMapping.get_package())) {
			return new MappingReference(MappingReference.Type.PACKAGE, jaxbMapping.get_package());
		}
		return null;
	}

}
