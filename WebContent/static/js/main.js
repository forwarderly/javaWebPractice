/**
 * author: gendseo
 * time: 2019-01-02
 * update: change to es6
 */

$(document).ready(function () {
  /**
   * 全局配置
   */
  var URL = 'http://localhost:8080/webPractice/BooksApi/';
  var studentid = $("#studentid");
  var studentname = $("#studentname");
  var institute= $("#institute");
  var course = $("#course");
  var intake = $("#intake");
  var BtnSubmit = $("#btn_submit");
  var myModal = $("#myModal");
  var myModalLabel = $("#myModalLabel");

  // 初始化表格插件
  InitTable();

  $("#btn_add").click(function () {
	course.css("border-color", "#ccc");
    var studentids = [];
    var getSelectRows = $("#table").bootstrapTable("getData", (books) => { return books });
    $.each(getSelectRows, (i, book) => { studentids.push(book.studentid) });
    
    	var max = Math.max.apply(null, studentids);
    	studentid.val(max + 1);
    
    studentname.val('');
    institute.val('');
	course.val('');
	intake.val('');
    myModalLabel.text('添加一门课程');
    BtnSubmit.attr("form", "INSERT");
    myModal.modal('show');
  });

  $("#btn_edit").click(function () {
	course.css("border-color", "#ccc");
    var getSelectRows = $("#table").bootstrapTable("getSelections", (books) => { return books });
    if (getSelectRows.length != 1) {
      alert("没有选中行或者选了多行");
    } else {
      var book = getSelectRows[0];
      studentid.val(book.studentid);
      studentname.val(book.studentname);
      institute.val(book.institute);
      course.val(book.course);
      intake.val(book.intake);
      myModalLabel.text('编辑学生信息');
      BtnSubmit.attr("form", "UPDATE");
      myModal.modal('show');
    }
  });

  BtnSubmit.click(function () {
    var btn_status = BtnSubmit.attr("form");
    var reg = new RegExp("^[0-9]*$");
    if (!reg.test(course.val()) || course.val() > 30) {
      alert("上课门数必须为0——30的整数");
      course.css("border-color", "#FF0000");
      course.val('');
      course.focus();
    } else if (studentname.val() === '' || studentname.val().length === 0) {
      alert("还没有输入学生姓名");
    } else if (course.val() === '' || course.val().length === 0) {
      alert("还没有输入上课门数");
    } else {
      if (btn_status === "UPDATE") {
        myModal.modal('hide');
        var UPDATE_JSON = {
          "studentid": parseInt(studentid.val()),
          "studentname": studentname.val().toString(),
          "institute":institute.val().toString(),
          "intake":intake.val().toString(),
          "course": parseInt(course.val()),
        }
        $.ajax({
          url: URL + 'UPDATE',
          type: "POST",
          dataType: "text",
          data: JSON.stringify(UPDATE_JSON),
          success: (result) => {
            if (result === "true") {
              $("#table").bootstrapTable("refresh", {});
            } else {
              alert("更新操作未成功");
            }
          },
        });
      }
      if (btn_status === "INSERT") {
        myModal.modal('hide');
        var INSERT_JSON = {
          "studentid": parseInt(studentid.val()),
          "studentname": studentname.val().toString(),
          "institute":institute.val().toString(),
          "intake":intake.val().toString(),
          "course": parseInt(course.val()),
        }
        $.ajax({
          url: URL + 'INSERT',
          type: "POST",
          dataType: "text",
          data: JSON.stringify(INSERT_JSON),
          success: (result) => {
            if (result === "true") {
              $("#table").bootstrapTable("refresh", {});
            } else {
              alert("增加操作未成功");
            }
          },
        });
      }
    }

  });

  $("#btn_delete").click(function () {
    var getSelectRows = $("#table").bootstrapTable("getSelections", (books) => { return books });
    if (getSelectRows.length > 0) {
      var s = "";
      $.each(getSelectRows, (i, book) => { s = s + book.studentid + ',' });
      s = s.substring(0, s.length - 1);
      $.post(URL + 'DELETE', { studentid: s }, (result) => {
          if (result === "true") {
            $("#table").bootstrapTable("refresh",
              function () {});
          }
      });
    } else {
      alert("未选择行");
    }
  });

  $('#btn_query').click(function () {
    $("#table").bootstrapTable("refresh", {});
  });

  function InitTable() {
    $('#table').bootstrapTable({
      url: URL,
      // 工具按钮的容器
      toolbar: '#toolbar',
      // 是否启用点击选中行
      clickToSelect: true,
      // 是否显示行间隔色
      striped: false,
      // 分页方式
      sidePagination: 'client',
      //是否显示分页（*）
      pagination: true,
      // 初始化table时显示的页码
      pageNumber: 1,
      // 每页条目
      pageSize: 10,
      //可供选择的每页的行数（*）
      pageList: [10, 25, 50, 100],
      // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      cache: false,
      // 是否启用排序
      sortable: true,
      // 排序方式
      sortOrder: "asc",
      //是否显示表格搜索
      search: false,
      // 是否显示所有的列
      showColumns: true,
      // 是否显示刷新按钮
      showRefresh: true,
      // 每一行的唯一标识，一般为主键列
      uniqueId: "studentid",
      // key值栏位
      idField: 'studentid',
      //字段和列名
      columns: [{
          checkbox: true,
        },
        {
          field: 'studentid',
          title: '学号',
          sortable: true,
        },
        {
          field: 'studentname',
          title: '学生姓名'
        },
        {
            field: 'institute',
            title: '学院',
            sortable: true,
          },
          {
              field: 'intake',
              title: '入学时间',
              sortable: true,
            },
        {
          field: 'course',
          title: '上课门数',
          sortable: true,
        },],
    });
  }
});