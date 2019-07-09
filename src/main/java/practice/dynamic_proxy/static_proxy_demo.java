package practice.dynamic_proxy;

public class static_proxy_demo implements service_demo {
    @Override
    public void dosth() {
        sdi.dosth();
    }

    private service_demo_impl sdi;
    public static_proxy_demo(service_demo_impl service_demo_impl) {
        sdi = service_demo_impl;
    }

    public static void main(String[] args) {
        service_demo_impl service_demo_impl = new service_demo_impl();
        static_proxy_demo static_proxy_demo = new static_proxy_demo(service_demo_impl);
        static_proxy_demo.dosth();
    }
}
