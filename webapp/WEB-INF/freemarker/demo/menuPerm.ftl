<div class="page-content">
    <div class="page-header">
        <h1>
            菜单操作选择控件
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-3">

        </div>

        <div class="col-sm-6 widget-container-col">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title lighter">菜单操作多选框</h4>

                    <div class="widget-toolbar no-border">
                        <ul class="nav nav-tabs" id="myTab2">
                            <li class="active">
                                <a data-toggle="tab" href="#home2">Menu</a>
                            </li>

                             <li>
                                  <a data-toggle="tab" href="#profile2">Perm</a>
                              </li>

                        </ul>
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right">
                        <div class="tab-content padding-4">
                            <div id="home2" class="tab-pane in active">
                                <!-- #section:custom/scrollbar.horizontal -->
                                <div class="scrollable-horizontal scrollable" data-size="1000">
                                    <ul id="tree1"></ul>
                                </div>

                                <!-- /section:custom/scrollbar.horizontal -->
                            </div>

                        <div id="profile2" class="tab-pane">
                             <div class="scrollable" data-size="1000" data-position="left">
                                 <ul id="tree2"></ul>
                             </div>
                         </div>

                        <#--  <div id="info2" class="tab-pane">
                             <div class="scrollable" data-size="1000">
                                 <b>Scroll # 3</b>
                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis. Nullam interdum massa vel nisl fringilla sed viverra erat tincidunt. Phasellus in ipsum velit. Maecenas id erat vel sem convallis blandit. Nunc aliquam enim ut arcu aliquet adipiscing. Fusce dignissim volutpat justo non consectetur.
                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis. Nullam interdum massa vel nisl fringilla sed viverra erat tincidunt. Phasellus in ipsum velit. Maecenas id erat vel sem convallis blandit. Nunc aliquam enim ut arcu aliquet adipiscing. Fusce dignissim volutpat justo non consectetur.
                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque commodo massa sed ipsum porttitor facilisis. Nullam interdum massa vel nisl fringilla sed viverra erat tincidunt. Phasellus in ipsum velit. Maecenas id erat vel sem convallis blandit. Nunc aliquam enim ut arcu aliquet adipiscing. Fusce dignissim volutpat justo non consectetur.
                             </div>
                         </div>-->
                        </div>
                    </div>
                    <div class="widget-toolbox padding-8 clearfix">
                        <button class="btn btn-xs btn-danger pull-right"  onclick=" $(this).closest('.widget-container-col').hide()">
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
        <div class="col-xs-12 col-sm-3">
        </div>
    </div>
</div>
<script type="text/javascript">
    function showMyItems(){
        var ids='';
        ids+="Menu:<br>";
        $.each($('#tree1').tree('selectedItems' ),function(n,value) {
            if(n>0) ids+='<br>';
            ids+=value.id;
        });
        ids+="<br>Perm:<br>";
        $.each($('#tree2').tree('selectedItems' ),function(n,value) {
            if(n>0) ids+='<br>';
            ids+=value.id;
        });

        bootbox.alert(ids);

    }


    jQuery(function($) {


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




        var remoteUrl = 'menu/treeComponentMenu';

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
                })
            }
        };

        var remoteUrl2 = 'perm/treeComponentPerm';

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
                        callback({ data: menudata });
                    },
                    error: function(response) {
                        //console.log(response);
                    }
                })
            }
        };




        var tree_options={
            dataSource: remoteDateSource,
            multiSelect: true,
            cacheItems: true,
            'open-icon' : 'ace-icon tree-minus',
            'close-icon' : 'ace-icon tree-plus',
            'selectable' : true,
            'selected-icon' :'ace-icon fa fa-check',
            'unselected-icon' :'ace-icon fa fa-times',
            loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            folderSelect: false
        };

        var tree_options2={
            dataSource: remoteDateSource2,
            multiSelect: true,
            cacheItems: true,
            'open-icon' : 'ace-icon tree-minus',
            'close-icon' : 'ace-icon tree-plus',
            'selectable' : true,
            'selected-icon' :'ace-icon fa fa-check',
            'unselected-icon' :'ace-icon fa fa-times',
            loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
            folderSelect: false
        };


        $('#tree1').ace_tree(tree_options);
        $('#tree2').ace_tree(tree_options2);


    });


</script>