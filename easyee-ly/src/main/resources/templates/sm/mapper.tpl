<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${pkgName}.dao.${ClassName}DAO">
	<resultMap type="${ClassName}" id="${ClassName}Map">
		<!-- mapping -->
		<#if Autos??>
		<#list Autos as item>
		<#assign info=item?split("#")>
		<result column="${info[8]}" property="${info[0]}"/>		
		</#list>
		</#if>
	</resultMap>
	
	<sql id="tableName">
		${table}
	</sql>

	<select id="findAll" resultMap="${ClassName}Map">
		select * from <include refid="tableName" />
	</select>
	
	<select id="get" resultMap="${ClassName}Map">
		select * from <include refid="tableName" /> where ${oidColumn}=${r"#{"}${Oid}}
	</select>
	
	<insert id="save">
		<selectKey keyColumn="${oidColumn}" keyProperty="${Oid}" resultType="String" order="BEFORE">
			select sys_guid() from dual
		</selectKey>
		insert into <include refid="tableName" />
		<trim prefix=" (" suffix=") " suffixOverrides=",">	<!-- suffixOverrides可以去掉多余的逗号"," -->
			<#if Autos??>
				<#list Autos as item>
					<#assign info=item?split("#")>
			<if test="${info[0]} != null">${info[8]},</if>
				</#list>
			</#if> 
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
			<#if Autos??>
				<#list Autos as item>
					<#assign info=item?split("#")>
			<if test="${info[0]} != null">${r"#{"}${info[0]}},</if>
				</#list>
			</#if>
		</trim>
	</insert>
	
	<update id="update">
		update <include refid="tableName" /> 
		<set>	<!-- set可以将动态的配置SET关键字，和剔除追加到条件末尾的任何不相关的逗号 -->
			<#if Autos??>
				<#list Autos as item>
					<#assign info=item?split("#")>
			<if test="${info[0]} != null">${info[8]}=${r"#{"}${info[0]}},</if>
				</#list>
			</#if>
		</set>
		where ${oidColumn}=${r"#{"}${Oid}}
	</update>
	
	<delete id="delete">
		delete from <include refid="tableName" /> where ${oidColumn}=${r"#{"}${Oid}}
	</delete>
	
	<delete id="deleteByIds">
		delete from <include refid="tableName" /> 
		where ${oidColumn} in
		<foreach item="item" collection="array" open="(" separator="," close=")">
			${r"#{"}item}
		</foreach>
	</delete>
	
	<select id="pagination" resultMap="${ClassName}Map">
		${r"${"}autoSQL}
	</select>

	<select id="findMaxRow" resultType="int">
		select count(*) from <include refid="tableName" />
	</select>
	
	<select id="query" resultMap="${ClassName}Map">
		select * from <include refid="tableName" />  
		<where>
			<if test="${Oid} != null">
				and ${oidColumn}=${r"#{"}${Oid}}
			</if>
		</where>
		order by ${oidColumn} ASC
	</select>
</mapper>