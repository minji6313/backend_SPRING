package hello.servlet.web.frontcontroller.v2;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV2", urlPatterns ="/front-controller/v2/*" ) //*: v1 하위에 어떤것이 들어오든 해당 메서드가 호출
public class FrontControllerServletV2 extends HttpServlet {

    /** 매핑정보 만들기
     * 어떤 url을 호출하게되면 ContollerV1을 호출
     * controllerMap
     * key: 매핑 URL
     * value: 호출될 컨트롤러 */
    private Map<String , ControllerV2> controllerMap = new HashMap<>();

    /** 매핑 정보를 생성할 때 미리 담기
     * 기본 생성자에 아래와 같이 put 메서드를 작성하면 서블릿이 생성될 때 해당 해당 패턴들에 미리 저장을 함*/
    public FrontControllerServletV2() {
            controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
            controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
            controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV2.service");

        //url 경로를 얻을 수 있습니다.예를들면, /front-controller/v1/members/new-form 해당 부분이 requestURI에 담김
        String requestURI = request.getRequestURI();

        //다형성에 의해 ControllerV1을 구현하고 있어 부모 타입인 ControllerV1 타입으로 초기화가 가능
        //ControllerV1 controller = new MemberListControllerV1();

        ControllerV2 controller = controllerMap.get(requestURI); //어떤 경로가 요청되냐에 따라서 이어진 객체 인스턴스가 반환됨 ex) /front-controller/v1/members/save 가 호출되면, new MemberSaveControllerV1()이게 호출

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request,response);


    }
}
