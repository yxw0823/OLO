<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	<!-- 新增-->
	<insert ${pk}="save" parameterType="pd">
		insert into ${tabletop}(
	<#list fieldList as var>
			${var[0]},	
	</#list>
			${pk}
		) values (
	<#list fieldList as var>
			${r"#{"}${var[0]}${r"}"},	
	</#list>
			${r"#{"}${pk}${r"}"}
		)
	</insert>
	
	
	<!-- 删除-->
	<delete ${pk}="delete" parameterType="pd">
		delete from ${tabletop}
		where 
			${pk} = ${r"#{"}${pk}${r"}"}
	</delete>
	
	
	<!-- 修改 -->
	<update ${pk}="edit" parameterType="pd">
		update  ${tabletop}
			set 
	<#list fieldList as var>
		<#if var[3] == "是">
				${var[0]} = ${r"#{"}${var[0]}${r"}"},
		</#if>
	</#list>
			${pk} = ${pk}
			where 
				${pk} = ${r"#{"}${pk}${r"}"}
	</update>
	
	
	<!-- 通过${pk}获取数据 -->
	<select ${pk}="findBy${pk}" parameterType="pd" resultType="pd">
		select 
	<#list fieldList as var>
			${var[0]},	
	</#list>
			${pk}
		from 
			${tabletop}
		where 
			${pk} = ${r"#{"}${pk}${r"}"}
	</select>
	
	
	<!-- 列表 -->
	<select ${pk}="datalistPage" parameterType="page" resultType="pd">
		select
		<#list fieldList as var>
				a.${var[0]},	
		</#list>
				a.${pk}
		from 
				${tabletop} a
	</select>
	
	<!-- 列表(全部) -->
	<select ${pk}="listAll" parameterType="pd" resultType="pd">
		select
		<#list fieldList as var>
				a.${var[0]},	
		</#list>
				a.${pk}
		from 
				${tabletop} a
	</select>
	
	<!-- 批量删除 -->
	<delete ${pk}="deleteAll" parameterType="String">
		delete from ${tabletop}
		where 
			${pk} in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>