package com.pieczykolan.apliakcjadoangielskiego.View;


import com.pieczykolan.apliakcjadoangielskiego.Entity.LevelsEntity;
import com.pieczykolan.apliakcjadoangielskiego.Entity.Teacher;
import com.pieczykolan.apliakcjadoangielskiego.Entity.User;
import com.pieczykolan.apliakcjadoangielskiego.Layout.MainLayoutTeacher;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.model.*;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "Teacher/group/:name", layout = MainLayoutTeacher.class)
@CssImport("themes/my-theme/teacher.css")
public class TeacherMainLayout extends VerticalLayout implements BeforeEnterObserver {

    private AuthService authService;
    private Teacher teacher;
    private String name;
    private Crud<Users> crud ;
    Chart chartNoun;
    Chart chartVerb;
    Chart chartAdjective;
    Chart chartAdverbial;
    private ComboBox<String> comboBoxUser = new ComboBox<>("Lista uczniów");
    private Button buttonAdd = new Button("Dodaj ucznia");
    private Label labelGroup;
    VerticalLayout verticalLayoutAddStudent = new VerticalLayout();
    HorizontalLayout horizontalLayoutAddStudentsAndCrud = new HorizontalLayout();
    HorizontalLayout horizontalLayoutForCharts = new HorizontalLayout();
    H1 title = new H1("English Study");
    List<LevelsEntity> arrayOfLevels;
    private boolean isCharts = true;



    @Autowired
    public TeacherMainLayout(AuthService authService) {
        this.authService = authService;
        teacher = VaadinSession.getCurrent().getAttribute(Teacher.class);
        setClassname();
        crud = new Crud<>(Users.class,createGrid(), creatEditor());
        verticalLayoutAddStudent.add(comboBoxUser,buttonAdd);
        horizontalLayoutAddStudentsAndCrud.add(verticalLayoutAddStudent, crud);
        setComboBoxUser();
        chartVerb = new Chart();
        chartNoun = new Chart();
        chartAdjective = new Chart();
        chartAdverbial = new Chart();
        horizontalLayoutForCharts.add(chartVerb,chartNoun,chartAdjective,chartAdverbial);
        add(horizontalLayoutAddStudentsAndCrud);
        add(horizontalLayoutForCharts);

    }
    @Override
    public void beforeEnter(BeforeEnterEvent event){
        name = event.getRouteParameters().get("name").orElse("main2");
        labelGroup = new Label(name);
        setButtonAdd();
        setCrud();
        if(isCharts) {
            setChart();
        }
        add(horizontalLayoutForCharts);

    }
    public void setChart(){
        horizontalLayoutForCharts.remove(chartVerb,chartNoun,chartAdjective,chartAdverbial);
        chartVerb = new Chart();
        chartNoun = new Chart();
        chartAdjective = new Chart();
        chartAdverbial = new Chart();
        List<DataCharts> noun = new ArrayList<>();
        List<DataCharts> verb = new ArrayList<>();
        List<DataCharts> adjective = new ArrayList<>();
        List<DataCharts> adverbial= new ArrayList<>();
        setDataCharts(noun,verb,adjective,adverbial);
        getLevelStateChart(noun,chartVerb);
        getLevelStateChart(verb,chartNoun);
        getLevelStateChart(adjective,chartAdjective);
        getLevelStateChart(adverbial,chartAdverbial);
        horizontalLayoutForCharts.add(chartVerb,chartNoun,chartAdjective,chartAdverbial);
    }
    public void setClassname(){
        verticalLayoutAddStudent.addClassName("verticalAddStudents");
        comboBoxUser.addClassName("combo-box");
        title.addClassName("title");
        buttonAdd.addClassName("buttonAdd");
        horizontalLayoutForCharts.addClassName("horizontalForCharts");
        horizontalLayoutAddStudentsAndCrud.addClassName("horizontalAddStudentsAndCrud");

    }
    private void getLevelStateChart(List<DataCharts> type,Chart chart){
        chart.addClassName("chart");
        Configuration conf = chart.getConfiguration();
        conf.setTitle((type.get(0).getTypeOfWord()).toString());
        conf.getChart().setType(ChartType.PIE);
        DataSeries nounType = new DataSeries();
        int [] countLevelCharts = new int[5];
        countLevelCharts = setCountLevelCharts(type);
        for(int i=0;i<5;i++) {
            if(countLevelCharts[i]>0) {
                nounType.add(new DataSeriesItem("level " + (i + 1), countLevelCharts[i]));
            }
        }
        conf.addSeries(nounType);
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        //plotOptions.setColors(SolidColor.RED,SolidColor.BLUE,SolidColor.GREEN,SolidColor.PURPLE,SolidColor.ORANGE);
        plotOptions.setInnerSize("40%");
        plotOptions.setSize("150px");
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels.setFormat("<b>{point.name}</b>: {point.percentage:.1f} %");
        plotOptions.setDataLabels(dataLabels);
        nounType.setPlotOptions(plotOptions);

    }

    public int [] setCountLevelCharts(List<DataCharts> type) {
        int [] countLevelCharts = new int[5];
        for(int i=0; i<type.size(); i++){
            countLevelCharts[type.get(i).getLevel()-1]++;
        }
        return countLevelCharts;
    }

    public void setDataCharts(List<DataCharts> noun, List<DataCharts> verb, List<DataCharts> adjective, List<DataCharts> adverbial){
        for(int i=0;i<arrayOfLevels.size();i++){
            noun.add(new DataCharts(TypeOfWord.NOUN,arrayOfLevels.get(i).getLevelOfNoun()));
            verb.add(new DataCharts(TypeOfWord.VERB,arrayOfLevels.get(i).getLevelOfVerb()));
            adjective.add(new DataCharts(TypeOfWord.ADJECTIVE,arrayOfLevels.get(i).getLevelOfAdjective()));
            adverbial.add(new DataCharts(TypeOfWord.ADVERBIAL,arrayOfLevels.get(i).getLevelOfAdverbial()));
        }
    }
    public void setButtonAdd(){
        buttonAdd.addClickListener(e ->{
                    authService.addTeacherGroupIdToUser(comboBoxUser.getValue().toString(),teacher.getId(),name);
                    setCrud();
                    setChart();
                    setComboBoxUser();
                }
                );
    }
    public void setComboBoxUser(){
        List<User> usersWithoutGroup = authService.getAllUsersWithoutGroup();
        List<String> tmp = new ArrayList<>();
        for(int i=0;i<usersWithoutGroup.size();i++) {
            tmp.add(usersWithoutGroup.get(i).getNickName());
        }
        comboBoxUser.setItems(tmp);
//        if(!comboBoxUser.isEmpty()){
//            buttonAdd.setEnabled(true);
//        }
    }
    public void setCrud(){
        crud.setDataProvider(setArrayOfCrudUsers());
        crud.setToolbarVisible(false);
        crud.setHeight(270, Unit.PIXELS);

    }
    public ListDataProvider<Users> setArrayOfCrudUsers(){
        List<Users> arrayOfCrudUsers = new ArrayList<>();
        List<User> arrayOfUser = authService.getAllUsersOfGroup(name);
        List<Integer> userWithGroupId = new ArrayList<>();
        if(arrayOfUser.size() == 0){
            isCharts = false;
        }else{
            isCharts = true;
        }
        for (int i=0;i<arrayOfUser.size();i++) {
            userWithGroupId.add(arrayOfUser.get(i).getId());
        }
        arrayOfLevels = authService.getAllLevelsOfGroup(userWithGroupId);
        for(int i=0;i<arrayOfUser.size();i++){
            Users users = new Users();
            users.setUsername(arrayOfUser.get(i).getNickName());
            users.setUserAvatar(authService.setImageFormDatabase(arrayOfUser.get(i).getId()));
            users.setVerb(arrayOfLevels.get(i).getLevelOfVerb());
            users.setNoun(arrayOfLevels.get(i).getLevelOfNoun());
            users.setAdjective(arrayOfLevels.get(i).getLevelOfAdjective());
            users.setAdverbial(arrayOfLevels.get(i).getLevelOfAdverbial());
            arrayOfCrudUsers.add(users);
            arrayOfCrudUsers.get(i).getUserAvatar().addClassName("user-avatar-crud");
        }
        return new ListDataProvider<>(arrayOfCrudUsers);
    }
    private CrudEditor<Users> creatEditor() {
        Label username =  new Label("Nazwa Użytkownika");
        Image userAvatar = new Image();
        //userAvatar.addClassName("user-avatar-crud");
        Label verb = new Label("Czasowniki");
        Label noun= new Label("Rzeczowniki");
        Label adjective = new Label("Przymiotniki");
        Label adverbial = new Label("Okoliczniki");
        FormLayout form = new FormLayout(username,userAvatar,verb,noun,adjective,adverbial);
        Binder<Users> binder = new Binder<>(Users.class);

        return new BinderCrudEditor<>(binder, form);

    }
    private Grid<Users> createGrid() {
        Grid<Users> grid = new Grid<>();
        grid.setWidth("700px");
        grid.addColumn(Users::getUsername).setHeader("Nazwa Użytkownika").setSortable(true)
                .setWidth("50px");
        grid.addComponentColumn(Users::getUserAvatar).setHeader("Avatar")
                .setWidth("50px");
        grid.addColumn(Users::getVerb).setHeader("Czasownik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getNoun).setHeader("Rzeczownik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getAdjective).setHeader("Przymiotnik").setSortable(true)
                .setWidth("50px");
        grid.addColumn(Users::getAdverbial).setHeader("Okolicznik").setSortable(true)
                .setWidth("50px");
        Crud.addEditColumn(grid);
        return grid;
    }


    public void removeCharts() {

    }
}
