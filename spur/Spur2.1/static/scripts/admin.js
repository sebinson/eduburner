;(function($){
    var $mainContent = null;
    var $entryAdminTable = null;
    var $resAdminTable = null;
    
    var Row = new Class({
        initialize: function(){
            this.$rowEl = $('<tr></tr>');
            this.$titleEl = $('<td class="list-cell list-cell-left-most"></td>');
            this.$operateEl = $('<td class="list-cell" style="text-align:center"></td>');
            
            this.$rowEl.append(this.$titleEl).append(this.$operateEl);
        },
        
        buildSepEl: function(){
            return $('<span>&nbsp;|&nbsp;</span>');
        },
        
        buildLinkEl: function(){
            return $('<a href="#"></a>');
        }
    });
    
    var EntryRow = new Class({
        Extends: Row,
        
        id: null,
        entry: null,
        status: 'DRAFT',
        
        initialize: function(entry){
            this.parent();
            this.id = entry.id;
            this.entry = entry;
            this.status = entry.status;
            this.buildEntryRow();
        },
        
        buildEntryRow: function(){
        
            var self = this;
            
            this.$titleEl.html(this.entry.title);
            
            var $removeEl = (this.$removeEl = this.buildLinkEl());
            var $approveEl = (this.$approveEl = this.buildLinkEl());
            var $editEl = this.buildLinkEl();
            
            $removeEl.html('删除').attr('href', '/remove').bind('click', this.onRemove.bind(this));
            
            if (this.status == 'DRAFT') {
                $approveEl.html('批准').attr('href', '/approve');
            }
            else {
                $approveEl.html('取消批准').attr('href', '/disapprove');
            }
            
            $approveEl.bind('click', this.toggleApprove.bind(this));
            
            $editEl.html('编缉').attr('href', ['/entries/', this.id, '/edit'].join(''));
            
            this.$operateEl.append($removeEl).append(this.buildSepEl()).append($approveEl).append(this.buildSepEl()).append($editEl);
            
        },
        
        onRemove: function(){
        	
        },
        
        toggleApprove: function(e){
			e.preventDefault();
            if (this.status == 'DRAFT') {
                this.approve();
            }
            else {
                this.disapprove();
            }
        },
        
        approve: function(){
            var self = this;
            $.waiting.start();
            var url = '/entries/' + this.entry.id;
            var self = this;
            $.ajax({
                url: url,
                type: 'POST',
                data: {
                    '_method': 'PUT',
                    'from': 'admin',
                    'operation': 'approve'
                },
                dataType: 'json',
                success: function(data){
                    $.waiting.stop();
                    if (data.msg == 'OK') {
                        self.$approveEl.html('取消批准').attr('href', '/disapprove');
                        self.status = 'PUBLISHED';
                    }
                }
            });
        },
        
        disapprove: function(e){
            e.preventDefault();
            var self = this;
            $.waiting.start();
            var url = '/entries/' + this.entry.id;
            var self = this;
            $.ajax({
                url: url,
                type: 'POST',
                data: {
                    '_method': 'PUT',
                    'from': 'admin',
                    'operation': 'disapprove'
                },
                dataType: 'json',
                success: function(data){
                    $.waiting.stop();
                    if (data.msg == 'OK') {
                        self.$approveEl.html('批准').attr('href', '/approve');
                        self.status = 'DRAFT';
                    }
                }
            });
        }
    })
    
    var ResRow = new Class({
        Extends: Row,
		
		id: null,
		res: null,
		status: null,
        
        initialize: function(res){
            this.parent();
			this.id = res.id;
			this.res = res;
			this.status = res.status;
			this.buildResRow();
        },
		
		buildResRow: function(){
			var self = this;
            
            this.$titleEl.html(this.res.url);
            
            var $removeEl = (this.$removeEl = this.buildLinkEl());
            var $approveEl = (this.$approveEl = this.buildLinkEl());
            var $editEl = this.buildLinkEl();
            
            $removeEl.html('删除').attr('href', '/remove').bind('click', this.onRemove.bind(this));
            
            if (this.status == 'DRAFT') {
                $approveEl.html('批准').attr('href', '/approve');
            }
            else {
                $approveEl.html('取消批准').attr('href', '/disapprove');
            }
            
            $approveEl.bind('click', this.toggleApprove.bind(this));
            
            $editEl.html('编缉').attr('href', ['/resources/', this.id, '/edit'].join(''));
            
            this.$operateEl.append($removeEl).append(this.buildSepEl()).append($approveEl).append(this.buildSepEl()).append($editEl);
		},
		
		onRemove: function(){
			
		},
		
		toggleApprove: function(e){
			e.preventDefault();
            if (this.status == 'DRAFT') {
                this.approve();
            }
            else {
                this.disapprove();
            }
        },
        
        approve: function(){
            var self = this;
            $.waiting.start();
            var url = '/resources/' + this.id;
            var self = this;
            $.ajax({
                url: url,
                type: 'POST',
                data: {
                    '_method': 'PUT',
                    'from': 'admin',
                    'operation': 'approve'
                },
                dataType: 'json',
                success: function(data){
                    $.waiting.stop();
                    if (data.msg == 'OK') {
                        self.$approveEl.html('取消批准').attr('href', '/disapprove');
                        self.status = 'PUBLISHED';
                    }
                }
            });
        },
        
        disapprove: function(e){
            e.preventDefault();
            var self = this;
            $.waiting.start();
            var url = '/resources/' + this.id;
            var self = this;
            $.ajax({
                url: url,
                type: 'POST',
                data: {
                    '_method': 'PUT',
                    'from': 'admin',
                    'operation': 'disapprove'
                },
                dataType: 'json',
                success: function(data){
                    $.waiting.stop();
                    if (data.msg == 'OK') {
                        self.$approveEl.html('批准').attr('href', '/approve');
                        self.status = 'DRAFT';
                    }
                }
            });
        }
    });
    
    var Admin = new function(){
        var self = this;
        
        var entryRowMap = new Hash();
		var resRowMap = new Hash();
        
        var init = function(){
            $.utils.setBodyClass();
            
            $mainContent = $('#main-content');
            $entryAdminTable = $('#entry-admin-table');
            $resAdminTable = $('#res-admin-table');
        }
        
        var bindEvents = function(){
            $('#manageEntry').bind('click', function(e){
                e.preventDefault();
                $.waiting.start();
                $.ajax({
                    url: '/entries/',
                    type: 'GET',
                    data: {
                        'from': 'admin'
                    },
                    dataType: 'json',
                    success: function(data){
                        $.waiting.stop();
                        if (data.msg == 'OK') {
                            $mainContent.html($('#entry-admin-table-area').val());
                            $.each(data.entries, function(e, entry){
                                var entryRow = new EntryRow(entry);
                                addEntryRow(entryRow);
                            });
                        }
                    }
                });
            });
            
            $('#manageRes').bind('click', function(e){
                e.preventDefault();
				$.waiting.start();
                $.ajax({
                    url: '/resources/',
                    type: 'GET',
                    data: {
                        'from': 'admin'
                    },
                    dataType: 'json',
                    success: function(data){
                        $.waiting.stop();
                        if (data.msg == 'OK') {
                            $mainContent.html($('#res-admin-table-area').val());
                            $.each(data.resources, function(e, res){
                                var resRow = new ResRow(res);
                                addResRow(resRow);
                            });
                        }
                    }
                });
            });
        };
        
        var addEntryRow = function(entryRow){
            entryRowMap[entryRow.id] = entryRow;
            $('#entry-admin-table>tbody').append(entryRow.$rowEl);
        };
		
		var addResRow = function(resRow){
			resRowMap[resRow.id] = resRow;
			$('#res-admin-table>tbody').append(resRow.$rowEl);
		};
        
        this.onDomReady = function(){
            init();
            bindEvents();
        };
    };
    
    $(document).ready(function(){
        Admin.onDomReady();
    });
    
})(jQuery)
