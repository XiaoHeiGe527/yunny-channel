
<#-- 基础URL -->
<#assign base_url = ''>
<#-- 上传文件访问URL -->
<#assign file_url = ''>

<#assign jsVersion = "1.0" >
<#assign cssVersion = "1.0" >

<#macro js_version paths>
	<#list paths as path>
		<script src="${base_url}/static/${path}?version=${jsVersion}"></script>
	</#list>
</#macro>
<#macro css_version paths>
	<#list paths as path>
		<link rel="stylesheet" href="${base_url}/static/${path}?version=${cssVersion}">
	</#list>
</#macro>



<#--
 * 从路径中获取文件名
 * ${getFileName(fileUrl)}
-->
<#function getFileName fileUrl >
    <#if fileUrl??>
		<#list fileUrl?split("/") as fname>
			<#if !fname_has_next>
			 	<#return fname>
			 </#if>
		</#list>
	</#if>
</#function>

<#--
 * 标题截取
 ${shortTitle(hisNav.title!'',20,1)}
-->
<#function shortTitle title maxLength=30 par=1>
    <#assign title=title?trim>
	<#if (title?length > maxLength  && par==1)>
		<#return "<span title='"+title+"'>"+title?substring(0,maxLength) + "...</span>">
	<#elseif (title?length > maxLength && par==2)>
		<#return title?substring(0,maxLength)>
	<#else>
		<#return title>
	</#if>
</#function>

<#--
 * 是否选中
 ${isChecked(model.configure!'',"1")}
-->
<#function isChecked list value >
    <#list list?split(",") as v>
		<#if v == value ><#return "checked"></#if>
	</#list>>
</#function>