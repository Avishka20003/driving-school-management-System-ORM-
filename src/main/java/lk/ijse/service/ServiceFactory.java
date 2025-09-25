package lk.ijse.service;


import lk.ijse.enums.ServiceTypes;
import lk.ijse.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return serviceFactory == null ? (serviceFactory = new ServiceFactory()) : serviceFactory;
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperService> T getService(ServiceTypes serviceTypes) {
        return switch (serviceTypes) {
            case COURSE ->  (T) new CourseServiceImpl();
            case  INSTRUCTOR -> (T) new InstructorServiceImpl();
            case  LESSONS -> (T) new LessonsServiceImpl();
            case PAYMENTS ->  (T) new PaymentsServiceImpl();
            case QUERY ->   (T) new QueryServiceImpl();
            case STUDENT -> (T) new StudentServiceImpl();
            case USER -> (T) new UserServiceImpl();
            default -> throw new IllegalArgumentException("Service not found");
        };
    }
}
