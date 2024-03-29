<html>
    <head>
        <!-- Bootstrap CSS Toolkit styles -->
        <!-- Generic page styles -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
        <!-- Bootstrap styles for responsive website layout, supporting different screen sizes -->
        <link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-responsive.min.css">
        <!-- Bootstrap CSS fixes for IE6 -->
        <!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
        <!-- Bootstrap Image Gallery styles -->
        <link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap.min.css">
        <!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-image-gallery.min.css">
        <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.fileupload-ui.css">
        <!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
        <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
            .sidebar-nav {
                padding: 9px 0;
            }
        </style>
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">Odontogramas</a>

                    <ul class="nav">
                        <li class="active"><a href="#">Inicio</a></li>
                        <li><a href="#about">Acerca de</a></li>
                        <li><a href="#contact">Contactenos</a></li>
                    </ul>

                    <ul class="nav pull-right">
                        <li id="fat-menu" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Bienvenido ${Usuario.nombreUsuario}<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Cambiar Contrase&ntilde;a</a></li>
                                <li class="divider"></li>
                                <li><a href="<%=request.getContextPath()%>/cerrarSesion.jsp">Cerrar Sesi&oacute;n</a></li>
                            </ul>

                        </li>
                    </ul>

                </div>
            </div>
        </div>


        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span3">
                    <div class="well sidebar-nav">
                        <ul class="nav nav-list">
                            <li class="nav-header">Menu</li>
                            <li><a href="/Odontogramas/#nuevoPaciente">Nuevo Paciente</a></li>
                            <li><a href="/Odontogramas/#listaPacientes">Lista de Pacientes</a></li>
                        </ul>
                    </div><!--/.well -->
                </div><!--/span-->
                <div id="contenido" class="span9">
                    <div class="container">

                        <!-- The file upload form used as target for the file upload widget -->
                        <form id="fileupload" action="<%=request.getContextPath()%>/Odontogramas" method="POST" enctype="multipart/form-data">
                            <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
                            <div class="row fileupload-buttonbar">
                                <div class="span7">
                                    <!-- The fileinput-button span is used to style the file input field as button -->
                                    <span class="btn btn-success fileinput-button">
                                        <i class="icon-plus icon-white"></i>
                                        <span>A�adir Archivos...</span>
                                        <input type="file" name="files[]" multiple>
                                    </span>
                                    <button type="submit" class="btn btn-primary start">
                                        <i class="icon-upload icon-white"></i>
                                        <span>Iniciar Subida</span>
                                    </button>
                                    <button type="reset" class="btn btn-warning cancel">
                                        <i class="icon-ban-circle icon-white"></i>
                                        <span>Cancelar subida</span>
                                    </button>
                                    <button type="button" class="btn btn-danger delete">
                                        <i class="icon-trash icon-white"></i>
                                        <span>Borrar</span>
                                    </button>
                                    <input type="checkbox" class="toggle">
                                </div>
                                <!-- The global progress information -->
                                <div class="span5 fileupload-progress fade">
                                    <!-- The global progress bar -->
                                    <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                                        <div class="bar" style="width:0%;"></div>
                                    </div>
                                    <!-- The extended global progress information -->
                                    <div class="progress-extended">&nbsp;</div>
                                </div>
                            </div>
                            <!-- The loading indicator is shown during file processing -->
                            <div class="fileupload-loading"></div>
                            <br>
                            <!-- The table listing the files available for upload/download -->
                            <table role="presentation" class="table table-striped">
                                <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
                                </tbody>
                            </table>
                        </form>
                        <br>
                        <div class="well">
                            <h3>Atenci�n!</h3>
                            <ul> 
                                <li>El tama�o m�ximo de archivos para subir es <strong>20 MB</strong>.</li>
                                <%--<li>Uploaded files will be deleted automatically after <strong>5 minutes</strong> (demo setting).</li>--%>
                                <li>Tu puedes <strong>arrastrar &amp; soltar</strong> archivos desde tu computador en �ste sitio con Google Chrome, Mozilla Firefox y Apple Safari.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div><!--/row-->



            <footer>
                <p>&copy; Universidad de Cartagena 2012</p>
            </footer>

        </div><!--/.fluid-container-->


        <div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">&times;</a>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body"><div class="modal-image"></div></div>
            <div class="modal-footer">
                <a class="btn modal-download" target="_blank">
                    <i class="icon-download"></i>
                    <span>Descargar</span>
                </a>
                <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
                    <i class="icon-play icon-white"></i>
                    <span>Slideshow</span>
                </a>
                <a class="btn btn-info modal-prev">
                    <i class="icon-arrow-left icon-white"></i>
                    <span>Anterior</span>
                </a>
                <a class="btn btn-primary modal-next">
                    <span>Siguiente</span>
                    <i class="icon-arrow-right icon-white"></i>
                </a>
            </div>
        </div></div>

    <!-- The template to display files available for upload -->
    <script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">

        <td class="preview"><span class="fade"></span></td>
        <td class="name"><span>{%=file.name%}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        {% if (file.error) { %}
        <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else if (o.files.valid && !i) { %}
        <td>
            <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
        </td>
        <td class="start">{% if (!o.options.autoUpload) { %}
            <button class="btn btn-primary">
                <i class="icon-upload icon-white"></i>
                <span>{%=locale.fileupload.start%}</span>
            </button>
            {% } %}</td>
        {% } else { %}
        <td colspan="2"></td>
        {% } %}
        <td class="cancel">{% if (!i) { %}
            <button class="btn btn-warning">
                <i class="icon-ban-circle icon-white"></i>
                <span>{%=locale.fileupload.cancel%}</span>
            </button>
            {% } %}</td>
    </tr>
    {% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        {% if (file.error) { %}
        <td></td>
        <td class="name"><span>{%=file.name%}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        <td class="error" colspan="2"><span class="label label-important">{%=locale.fileupload.error%}</span> {%=locale.fileupload.errors[file.error] || file.error%}</td>
        {% } else { %}
        <td class="preview">{% if (file.thumbnail_url) { %}
            <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
            {% } %}</td>
        <td class="name">
            <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
        </td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        <td colspan="2"></td>
        {% } %}
        <td class="delete">
            <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}">
                <i class="icon-trash icon-white"></i>
                <span>{%=locale.fileupload.destroy%}</span>
            </button>
            <input type="checkbox" name="delete" value="1">
        </td>
    </tr>
    {% } %}
</script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/jquery.ba-hashchange.js"></script>

<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="<%=request.getContextPath()%>/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="js/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="js/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS and Bootstrap Image Gallery are not required, but included for the demo -->
<script src="js/bootstrap.min.js"></script>
<script src="js/load-image.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-image-gallery.min.js"></script>



<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="<%=request.getContextPath()%>/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="<%=request.getContextPath()%>/js/jquery.fileupload.js"></script>
<!-- The File Upload file processing plugin -->
<script src="<%=request.getContextPath()%>/js/jquery.fileupload-fp.js"></script>
<!-- The File Upload user interface plugin -->
<script src="<%=request.getContextPath()%>/js/jquery.fileupload-ui.js"></script>
<!-- The localization script -->
<script src="<%=request.getContextPath()%>/js/locale.js"></script>
<!-- The main application script -->
<script src="<%=request.getContextPath()%>/js/main.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
<!--[if gte IE 8]><script src="js/cors/jquery.xdr-transport.js"></script><![endif]-->
</body>
</html>