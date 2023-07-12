package com.pieczykolan.apliakcjadoangielskiego.Layout;

import com.pieczykolan.apliakcjadoangielskiego.Entity.Teacher;
import com.pieczykolan.apliakcjadoangielskiego.Entity.TeacherGroupEntity;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.View.TeacherMainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Route("TeacherView")
@CssImport("themes/my-theme/teacherMainLayout.css")
public class MainLayoutTeacher extends AppLayout {
    private Teacher teacher;
    private TeacherMainLayout teacherMainLayout;
    private List<String> arrayOfTeacherGroup = new ArrayList();
    private Image avatarImage;
    private Label labelNickName = new Label();
    TextField textFieldGroupName = new TextField("Podaj nazwę grupy");
    Button buttonAddGroup = new Button("Dodaj grupe");
    private Button buttonLogout = new Button("Wyloguj się");
    H1 title = new H1("English Study");
    private AuthService authService;
    @Autowired
    public MainLayoutTeacher(AuthService authService) {
        this.authService = authService;
        teacher = VaadinSession.getCurrent().getAttribute(Teacher.class);
        teacherMainLayout = VaadinSession.getCurrent().getAttribute(TeacherMainLayout.class);
        title.addClassName("title");
        buttonAddGroup.addClickListener( e ->{
            authService.addGroupForTeacher(textFieldGroupName.getValue(),teacher);
        });
        avatarImage = authService.setImageFormDatabase(teacher.getId());
        avatarImage.addClassName("avatarImage");
        labelNickName.setText(teacher.getNickName());
        labelNickName.addClassName("labelNickName");
        buttonLogout.addClassName("buttonLogout");
        buttonLogout.addClickListener( e ->{
            getUI().get().getSession().close();
            UI.getCurrent().getPage().setLocation("/");
            VaadinSession.getCurrent().setAttribute(Teacher.class, null);
        });

        Tabs tabs = getTabs();
        addToDrawer(textFieldGroupName,buttonAddGroup,tabs);
        addToNavbar(title,labelNickName,avatarImage,buttonLogout);
    }
    private Tabs getTabs() {
        setArrayOfTeacherGroup();
        Tabs tabs = new Tabs();
        for(int i=0;i<arrayOfTeacherGroup.size();i++) {
            tabs.add(creatTab(VaadinIcon.GROUP, arrayOfTeacherGroup.get(i)));
        }
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addClassName("tabs");
        return tabs;
    }
    private Tab creatTab(VaadinIcon vaadinIcon, String groupName) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.setRoute(TeacherMainLayout.class, new RouteParameters("name", groupName));
        link.add(icon, new Span(groupName));
        link.setTabIndex(-1);

        return new Tab(link);
    }
    public void setArrayOfTeacherGroup(){
        arrayOfTeacherGroup = authService.getAllGroupNameByTeacherId(teacher.getId());
    }
}
