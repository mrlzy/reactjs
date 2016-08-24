<div class="page-content">
    <div class="page-header">
        <h1>
            用户管理
        </h1>
    </div>
    <div class="row">
            <div class="col-sm-3">
                <div class="widget-box widget-color-blue2">
                    <div class="widget-header">

                        <div class="nav-search" id="nav-search" style="font-size: 17px;top: 5px;  left: 5px;margin-top: 10;margin-bottom: 10px;font-weight: lighter;line-height: 1.1">
                                <form class="form-search"  onsubmit="return false;">
                                   部门组织
                                    <span class="input-icon" >
                                        <input type="text" class="nav-search-input"   id="search-input" onkeypress="if(event.keyCode==13){searchBlur(this.value)}"   />
                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                    </span>
                                </form>
                        </div>

                    </div>

                    <div class="widget-body">
                        <div class="widget-main padding-8">
                            <ul id="tree1"></ul>
                        </div>
                    </div>
                </div>

            </div>

        <div class="col-sm-9">
            <table id="grid-table"></table>
            <div id="grid-pager"></div>
        </div>


        <div  id="role_win" class="col-sm-6 widget-container-col" style=" position: absolute;  z-index: 4444;float: right;right: 200px;">

                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title lighter">请选择角色</h4>

                        <div class="widget-toolbar no-border">
                            <ul class="nav nav-tabs" id="myTab2">
                                <li class="active">
                                    <a data-toggle="tab" href="#home2">Role</a>
                                </li>

                            </ul>
                        </div>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main padding-12 no-padding-left no-padding-right">
                            <div class="tab-content padding-4">
                                <div id="home2" class="tab-pane in active">
                                    <div class="scrollable-horizontal scrollable" data-size="1000">
                                        <ul id="tree2"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="widget-toolbox padding-8 clearfix">
                            <button class="btn btn-xs btn-danger pull-right"  onclick=" $('#role_win').hide()">
                                <i class="ace-icon fa fa-times"></i>
                                <span class="bigger-110">关闭</span>
                            </button>

                            <button class="btn btn-xs btn-success pull-left" onclick="showMyItems()" >
                                <span class="bigger-110">确定</span>

                                <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                            </button>
                        </div>

                    </div>
                </div>
            </div>



    </div>
</div>
<script type="text/javascript">

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";


    var userId="";

    function showMyItems(){
        var items = $('#tree2').tree('selectedItems' );
        if(items.length==0){
            bootbox.alert('请选择你要的角色!');
            return ;
        }
        var ids=items[0].id;
        $.ajax({
            url: 'user/authorizeRole',
            data: 'role_id='+ids+"&user_id="+userId,
            type: 'POST',
            dataType: 'json',
            success : function(result) {
                if(!result.success){
                    bootbox.alert(result.msg);
                }else{
                    $(grid_selector).trigger("reloadGrid");
                }

                $('#role_win').hide();

            },
            error: function(response) {
                //console.log(response);
            }
        });

    }



    function showRole(user_id){
        var fs=$('#role_win').find('.ace');
        $.each(fs, function (index, value) {
            if(value.checked){
                $('#role_win').tree('selectFolder',$(value));
            }
        });
        var $sels =$('#role_win').find('.tree-selected');
        $sels.removeClass('tree-selected');
        userId=user_id;
        $('#role_win').show();

    }

    function addUser(org_id,org_name){
        $(grid_selector).jqGrid("editGridRow","new",{
            closeAfterAdd: true,
            recreateForm: true,
            viewPagerButtons: false,
            afterSubmit:function (response, postdata){
                var obj =eval('('+response.responseText+')');
                if(obj.success){
                    var  $target= $('#itme_'+org_id);
                    if($target){
                        $('#tree1').tree("openFolder",$target);
                        $target.closest('.tree-branch').find('.tree-branch-children').empty(); // tree branch
                        $('#tree1').tree("openFolder",$target);
                    }


                }
                return [obj.success,obj.msg];
            },
            beforeShowForm : function(e) {
                var form = $(e[0]);
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                        .wrapInner('<div class="widget-header" />')

                form.find('#org_name').val(org_name);

                form.find('#org_id').val(org_id);

            }

        });

    }


    function searchBlur(v){
        if(v==''){
            bootbox.alert("输入不能为空!!");
            return;
        }

        $.ajax({
            url: 'org/selectOrgByName',
            data: 'name='+v,
            type: 'POST',
            dataType: 'json',
            success : function(result) {
                if(result.length==0){
                    bootbox.alert('没找到['+name+']相关部门');
                } else{
                       var str="";
                       for(var i=0;i<result.length;i++){
                           var ws=result[i].split('_');
                           if(ws.length==2){
                               if(str!='') str+='<br>';
                               var wws=ws[0].split('=>');
                               var g=wws[wws.length-1]||ws[0];
                               str+='<a href="javascript:addUser(\''+ws[1]+'\',\''+g+'\')">'+ws[0]+'</a>';
                           }
                       }
                       bootbox.alert(str);

                }
            }
        });
    }


    jQuery(function($) {



       $('#role_win').hide();





        //resize to fit page size
        $(window).on('resize.jqGrid', function () {
            $(grid_selector).jqGrid( 'setGridWidth',$(grid_selector).parents('.col-sm-9').width()*0.99 );
        })
        //resize on sidebar collapse/expand
        var parent_column = $(grid_selector).closest('[class*="col-"]');
        $(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
            if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
                //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
                setTimeout(function() {
                    $(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
                }, 0);
            }
        })

        //if your grid is inside another element, for example a tab pane, you should use its parent's width:
        /**
         $(window).on('resize.jqGrid', function () {
					var parent_width = $(grid_selector).closest('.tab-pane').width();
					$(grid_selector).jqGrid( 'setGridWidth', parent_width );
				})
         //and also set width when tab pane becomes visible
         $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
				  if($(e.target).attr('href') == '#mygrid') {
					var parent_width = $(grid_selector).closest('.tab-pane').width();
					$(grid_selector).jqGrid( 'setGridWidth', parent_width );
				  }
				})
         */





        jQuery(grid_selector).jqGrid({
            //direction: "rtl",

            //subgrid options
            subGrid : false,
            //subGridModel: [{ name : ['No','Item Name','Qty'], width : [55,200,80] }],
            //datatype: "xml",
            /*subGridOptions : {
                plusicon : "ace-icon fa fa-plus center bigger-110 blue",
                minusicon  : "ace-icon fa fa-minus center bigger-110 blue",
                openicon : "ace-icon fa fa-chevron-right center orange"
            },*/
            //for this example we are using local data
          /*  subGridRowExpanded: function (subgridDivId, rowId) {
                var subgridTableId = subgridDivId + "_t";
                $("#" + subgridDivId).html("<table id='" + subgridTableId + "'></table>");
                $("#" + subgridTableId).jqGrid({
                    datatype: 'local',
                    data: subgrid_data,
                    colNames: ['No','Item Name','Qty'],
                    colModel: [
                        { name: 'id', width: 50 },
                        { name: 'name', width: 150 },
                        { name: 'qty', width: 50 }
                    ]
                });
            },*/
            url:  'user/listUserForTree',
            mtype: 'POST',
            datatype: 'json',
            jsonReader: {
                id:"user_id",
                root: "rows",   // json中代表实际模型数据的入口
                page: "page",   // json中代表当前页码的数据
                total: "total", // json中代表页码总数的数据
                records: "records", // json中代表数据行总数的数据
                repeatitems: false
            },
            height: 250,
            colNames:['用户编号','用户帐号','用户名称','角色','部门编号','部门名称','手机号','locked','email'],
            colModel:[

                {name:'user_id',index:'user_id', width:80, sorttype:"int", editable: true, hidden :true, align:"center"},
                {name:'account',index:'account', width:80, search:true, searchoptions:{sopt:["eq","ne","cn","nc","bw","bn","ew","en"]},    editable: true,editoptions:{size:"20",maxlength:"30"},
                    editrules:{
                         required:true,
                         custom:true,
                         custom_func:function (val, nm) {
                            if(/^[A-Za-z0-9_]{5,}$/.test(val)){
                                return [true,""];
                            }
                            return[false,nm+':只能包含[A-Za-z0-9_],且长度大于5'];
                     }},align:"center"
                },
                {name:'user_name',index:'user_name', width:80, search:true, searchoptions:{sopt:["eq","ne","cn","nc","bw","bn","ew","en"]},   editable: true,editoptions:{size:"20",maxlength:"30"},editrules:{required:true},align:"center"	},
                {name:'role_name',width:180, sortable:false, editable: false, sortable:false, align:"center",
                    formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==null||cellvalue=='')return '<a href="javascript:showRole(\''+rowObject.user_id+'\')">设置角色</a>';
                        return  cellvalue+'[<a href="javascript:showRole(\''+rowObject.user_id+'\')">切换</a>]';
                    }
                },
                {name:'org_id',index:'org_id', width:80, sorttype:"int", editable: true, hidden :true, align:"center"},
                {name:'org_name',index:'org_name',width:80,search:true, searchoptions:{sopt:["eq","ne","cn","nc","bw","bn","ew","en"]},editable: true,align:"center",editoptions:{readonly:true}},
                {name:'tel',index:'tel',search:false, width:80, editable: true,sorttype:"int",editrules:{number:true},editoptions:{size:"20",maxlength:"11"},align:"center"},
                {name:'locked',index:'locked', width:80,search:true,stype:'select',  searchoptions:{sopt:["eq"],value:{0:"否",1:"是"}},  editable: true,edittype:"select",editoptions:{value:"0:否;1:是"},align:"center",formatter:function(cellvalue){if(cellvalue==0)return '否';return '是';}},
                {name:'email',index:'email', width:80, search:false,   editable: true,editoptions:{size:"20",maxlength:"30"},align:"center"	},
            ],

            viewrecords : true,
            rowNum:10,
            rowList:[10,20,30],
            pager : pager_selector,
            altRows: true,
            //toppager: true,

            multiselect: true,
            //multikey: "ctrlKey",
            multiboxonly: true,

            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    styleCheckbox(table);

                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },

            editurl: "user/opUser",//nothing is saved
            caption: "用户列表"

            //,autowidth: true,


            /**
             ,
             grouping:true,
             groupingView : {
						 groupField : ['name'],
						 groupDataSorted : true,
						 plusicon : 'fa fa-chevron-down bigger-110',
						 minusicon : 'fa fa-chevron-up bigger-110'
					},
             caption: "Grouping"
             */

        });
        $(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size



        //enable search/filter toolbar
        //jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
        //jQuery(grid_selector).filterToolbar({});


        //switch element when editing inline
        function aceSwitch( cellvalue, options, cell ) {
            setTimeout(function(){
                $(cell) .find('input[type=checkbox]')
                        .addClass('ace ace-switch ace-switch-5')
                        .after('<span class="lbl"></span>');
            }, 0);
        }
        //enable datepicker
        function pickDate( cellvalue, options, cell ) {
            setTimeout(function(){
                $(cell) .find('input[type=text]')
                        .datepicker({format:'yyyy-mm-dd' , autoclose:true});
            }, 0);
        }


        //navButtons
        jQuery(grid_selector).jqGrid('navGrid',pager_selector,
                { 	//navbar options
                    edit: true,
                    editicon : 'ace-icon fa fa-pencil blue',
                    add: false,
                    addicon : 'ace-icon fa fa-plus-circle purple',
                    del: false,
                    delicon : 'ace-icon fa fa-trash-o red',
                    search: true,
                    searchicon : 'ace-icon fa fa-search orange',
                    refresh: true,
                    refreshicon : 'ace-icon fa fa-refresh green',
                    view: true,
                    viewicon : 'ace-icon fa fa-search-plus grey',
                },
                {
                    //edit record form
                    closeAfterEdit: true,
                    //width: 700,
                    recreateForm: true,
                    afterSubmit:function (response, postdata){
                        var obj =eval('('+response.responseText+')');
                        if(obj.success){
                              $('#itme_'+postdata.user_id).text(postdata.user_name);
                        }
                        return [obj.success,obj.msg];
                    },
                    beforeShowForm : function(e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                        style_edit_form(form);
                    }
                },
                {
                    //new record form
                    //width: 700,
                    closeAfterAdd: true,
                    recreateForm: true,
                    viewPagerButtons: false,
                    beforeShowForm : function(e) {
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                                .wrapInner('<div class="widget-header" />')
                        style_edit_form(form);
                    }
                },
                {
                    //delete record form
                    recreateForm: true,
                    beforeShowForm : function(e) {
                        var form = $(e[0]);
                        if(form.data('styled')) return false;

                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
                        style_delete_form(form);

                        form.data('styled', true);
                    },
                    onClick : function(e) {
                        //alert(1);
                    }
                },
                {
                    //search form
                    recreateForm: true,
                    afterShowSearch: function(e){
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                        style_search_form(form);
                    },
                    afterRedraw: function(){
                        style_search_filters($(this));
                    }
                    ,
                    multipleGroup:false,
                    showQuery: false,
                    multipleSearch: true

                },
                {
                    //view record form
                    recreateForm: true,
                    beforeShowForm: function(e){
                        var form = $(e[0]);
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
                    }
                }
        )



        function style_edit_form(form) {
            //enable datepicker on "sdate" field and switches for "stock" field
            form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})

            form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
            //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
            //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');


            //update buttons classes
            var buttons = form.next().find('.EditButton .fm-button');
            buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
            buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
            buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

            buttons = form.next().find('.navButton a');
            buttons.find('.ui-icon').hide();
            buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
            buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
        }

        function style_delete_form(form) {
            var buttons = form.next().find('.EditButton .fm-button');
            buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
            buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
            buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
        }

        function style_search_filters(form) {
            form.find('.delete-rule').val('X');
            form.find('.add-rule').addClass('btn btn-xs btn-primary');
            form.find('.add-group').addClass('btn btn-xs btn-success');
            form.find('.delete-group').addClass('btn btn-xs btn-danger');
        }
        function style_search_form(form) {
            var dialog = form.closest('.ui-jqdialog');
            var buttons = dialog.find('.EditTable')
            buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
            buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
            buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
        }

        function beforeDeleteCallback(e) {
            var form = $(e[0]);
            if(form.data('styled')) return false;

            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_delete_form(form);

            form.data('styled', true);
        }

        function beforeEditCallback(e) {
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_edit_form(form);
        }



        //it causes some flicker when reloading or navigating grid
        //it may be possible to have some custom formatter to do this as the grid is being created to prevent this
        //or go back to default browser checkbox styles for the grid
        function styleCheckbox(table) {
            /**
             $(table).find('input:checkbox').addClass('ace')
             .wrap('<label />')
             .after('<span class="lbl align-top" />')


             $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
             .find('input.cbox[type=checkbox]').addClass('ace')
             .wrap('<label />').after('<span class="lbl align-top" />');
             */
        }


        //unlike navButtons icons, action icons in rows seem to be hard-coded
        //you can change them like this in here if you want
        function updateActionIcons(table) {
            /**
             var replacement =
             {
                 'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
                 'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
                 'ui-icon-disk' : 'ace-icon fa fa-check green',
                 'ui-icon-cancel' : 'ace-icon fa fa-times red'
             };
             $(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
             */
        }

        //replace icons with FontAwesome icons like above
        function updatePagerIcons(table) {
            var replacement =
            {
                'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
                'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
                'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
                'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
            };
            $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
                var icon = $(this);
                var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

                if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
            })
        }

        function enableTooltips(table) {
            $('.navtable .ui-pg-button').tooltip({container:'body'});
            $(table).find('.ui-pg-div').tooltip({container:'body'});
        }

        //var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

        $(document).one('ajaxloadstart.page', function(e) {
            $(grid_selector).jqGrid('GridUnload');
            $('.ui-jqdialog').remove();
        });


        var remoteUrl = 'user/treeComponentOrgUser';

        var remoteDateSource = function(options, callback) {
            var parent_id = null;
            if( !('text' in options || 'type' in options) ){
                parent_id = 0;//load first level data
            }
            else if('type' in options && options['type'] == 'folder') {//it has children
                if('id' in options)
                    parent_id = options.id;
            }
            if(parent_id !== null) {
                $.ajax({
                    url: remoteUrl,
                    data: 'parent_id='+parent_id,
                    type: 'POST',
                    dataType: 'json',
                    success : function(menudata) {
                        callback({ data: menudata });
                    },
                    error: function(response) {
                        //console.log(response);
                    }
                });
            }
        };



        var tree_options={
            dataSource: remoteDateSource,
            multiSelect: false,
            cacheItems: true,
            'open-icon' : 'ace-icon tree-minus',
            'close-icon' : 'ace-icon tree-plus',
            'selectable' : false,
            'selected-icon' : '',
            'unselected-icon' : '',
            loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            folderSelect: false
        };

        $('#tree1').ace_tree(tree_options);


        $('#tree1').on('opened.fu.tree',
                function(e,result) {
                    $("#grid-table").jqGrid("setGridParam", { postData: {parent_id:result.id} }).trigger("reloadGrid");
                }
        );


        var h=$(".row").height()-$(".widget-header").height();
        $('#tree1').closest(".widget-body").height(h);
        $('#tree1').closest(".widget-body").css("overflow","auto");


        var CLIPBOARD="";
        var CLIPBOARDID="";
        var CLIPBOARDOP="";

        $('#tree1').contextmenu({
            delegate: ".tree-label",
            autoFocus: true,
            preventContextMenuForPopup: true,
            preventSelect: true,
            taphold: true,
            menu: [
                {title: "新增", cmd: "add"},
                {title: "剪切", cmd: "cut"},
                {title: "粘贴", cmd: "paste",  disabled: true }
            ],
            // Handle menu selection to implement a fake-clipboard
            select: function(event, ui) {
                var $target = ui.target;
                switch(ui.cmd){
                    case "add":
                        $(grid_selector).jqGrid("editGridRow","new",{
                            closeAfterAdd: true,
                            recreateForm: true,
                            viewPagerButtons: false,
                            afterSubmit:function (response, postdata){
                                var obj =eval('('+response.responseText+')');
                                if(obj.success){
                                    // alert(postdata.parent_id);
                                    $('#tree1').tree("openFolder",$target);
                                    $target.closest('.tree-branch').find('.tree-branch-children').empty(); // tree branch
                                    $('#tree1').tree("openFolder",$target);

                                }
                                return [obj.success,obj.msg];
                            },
                            beforeShowForm : function(e) {
                                var form = $(e[0]);
                                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                                        .wrapInner('<div class="widget-header" />')

                                form.find('#org_name').val($target.text());

                                form.find('#org_id').val($target.closest("li[role='treeitem']").data().id);

                                style_edit_form(form);
                            }

                        });

                        break;
                    case "cut":
                        CLIPBOARDID=$target.attr('id');
                        CLIPBOARD = $target.text();
                        CLIPBOARDOP = ui.cmd;
                        break;
                    case "paste":
                        $.ajax({
                            url: 'user/paste',
                            data: 'cmd='+CLIPBOARDOP+'&temp_id='+CLIPBOARDID+'&id='+$target.attr('id'),
                            type: 'POST',
                            dataType: 'json',
                            success : function(dd) {
                                if(!dd.success){
                                    bootbox.alert(dd.msg);
                                }else{
                                    $('#tree1').data("fu.tree",null);
                                    $('#tree1').off("click");
                                    $('#tree1').ace_tree(tree_options);
                                    CLIPBOARDID="";
                                    CLIPBOARD = "";
                                    CLIPBOARDOP ="";
                                }

                            },
                            error: function(response) {
                                //console.log(response);
                            }
                        });


                        break;


                }
                //alert("select " + ui.cmd + " on " + $target.text());
                // Optionally return false, to prevent closing the menu now
            },
            // Implement the beforeOpen callback to dynamically change the entries
            beforeOpen: function(event, ui) {
                var $menu = ui.menu,
                        $target = ui.target,
                        extraData = ui.extraData; // passed when menu was opened by call to open()

                ui.menu.zIndex( $(event.target).zIndex() + 1);
                var isfolder= $($target).prev(".icon-folder").length>0?true:false;
                $('#tree1').contextmenu("setEntry", "cut", "剪切 '" + $target.text() + "'")
                        .contextmenu("setEntry", "paste", "粘贴" + (CLIPBOARD ? " '" + CLIPBOARD + "'" : ""));
                if(!isfolder){
                    $('#tree1') .contextmenu("enableEntry", "paste", false)
                                 .contextmenu("enableEntry", "add", false)
                                 .contextmenu("enableEntry", "cut", true);
                }else{
                    $('#tree1') .contextmenu("enableEntry", "add", true)
                                    .contextmenu("enableEntry", "cut", false);

                    var enable=true;
                    if(CLIPBOARD==""){//粘贴板中没有数据
                        enable=false;
                    }else{
                        if(CLIPBOARDOP=='cut'){//剪切操作
                            if(CLIPBOARDID==$target.attr('id')){ //无法将自己切到自己中
                                enable=false;
                            }else{
                                var $ptarget=$('#'+CLIPBOARDID).closest('.tree-branch-children').prev().find('.tree-label');
                                if($ptarget.attr('id')==$target.attr('id')){//无法将自己原地移动
                                    enable=false;
                                }
                            }
                        }

                    }
                    $('#tree1') .contextmenu("enableEntry", "paste", enable);
                }

            }



        });

        // scrollables
        $('.scrollable').each(function () {
            var $this = $(this);
            $(this).ace_scroll({
                size: $this.attr('data-size') || 500,
                //styleClass: 'scroll-left scroll-margin scroll-thin scroll-dark scroll-light no-track scroll-visible'
            });
        });
        $('.scrollable-horizontal').each(function () {
            var $this = $(this);
            $(this).ace_scroll(
                    {
                        horizontal: true,
                        size: $this.attr('data-size') || 500,
                        mouseWheelLock: true
                    }
            ).css({'padding-top': 12});
        });

        $(window).on('resize.scroll_reset', function() {
            $('.scrollable-horizontal').ace_scroll('reset');
        });


        // widget boxes
        // widget box drag & drop example
        $('.widget-container-col').sortable({
            connectWith: '.widget-container-col',
            items:'> .widget-box',
            handle: ace.vars['touch'] ? '.widget-header' : false,
            cancel: '.fullscreen',
            opacity:0.8,
            revert:true,
            forceHelperSize:true,
            placeholder: 'widget-placeholder',
            forcePlaceholderSize:true,
            tolerance:'pointer',
            start: function(event, ui) {
                //when an element is moved, it's parent becomes empty with almost zero height.
                //we set a min-height for it to be large enough so that later we can easily drop elements back onto it
                ui.item.parent().css({'min-height':ui.item.height()})
                //ui.sender.css({'min-height':ui.item.height() , 'background-color' : '#F5F5F5'})
            },
            update: function(event, ui) {
                ui.item.parent({'min-height':''})
                //p.style.removeProperty('background-color');
            }
        });




        var remoteUrl2 = 'role/treeComponentRole';

        var remoteDateSource2 = function(options, callback) {
            var parent_id = null;
            if( !('text' in options || 'type' in options) ){
                parent_id = 0;//load first level data
            }
            else if('type' in options && options['type'] == 'folder') {//it has children
                if('id' in options)
                    parent_id = options.id;
            }
            if(parent_id !== null) {
                $.ajax({
                    url: remoteUrl2,
                    data: 'parent_id='+parent_id,
                    type: 'POST',
                    dataType: 'json',
                    success : function(menudata) {
                        for(var m in menudata){
                            menudata[m]['type']='folder';
                        }
                        callback({ data: menudata });
                    },
                    error: function(response) {
                        //console.log(response);
                    }
                });
            }
        };



        var tree_options2={
            dataSource: remoteDateSource2,
            multiSelect: false,
            folderSelect: true,
            cacheItems: true,
            'open-icon' : 'ace-icon tree-minus',
            'close-icon' : 'ace-icon tree-plus',
            'selectable' : true,
            'selected-icon' :'',
            'unselected-icon' :'',
            loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
        };

        $('#tree2').ace_tree(tree_options2);


    });
</script>
