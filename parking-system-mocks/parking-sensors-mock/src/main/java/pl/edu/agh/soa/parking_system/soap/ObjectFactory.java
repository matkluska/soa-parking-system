
package pl.edu.agh.soa.parking_system.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the pl.edu.agh.soa.parking_system.soap package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TogglePLace_QNAME = new QName("http://soap.parking_system.soa.agh.edu.pl/", "togglePLace");
    private final static QName _TogglePLaceResponse_QNAME = new QName("http://soap.parking_system.soa.agh.edu.pl/", "togglePLaceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pl.edu.agh.soa.parking_system.soap
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TogglePLace }
     */
    public TogglePLace createTogglePLace() {
        return new TogglePLace();
    }

    /**
     * Create an instance of {@link TogglePLaceResponse }
     */
    public TogglePLaceResponse createTogglePLaceResponse() {
        return new TogglePLaceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TogglePLace }{@code >}}
     */
    @XmlElementDecl(namespace = "http://soap.parking_system.soa.agh.edu.pl/", name = "togglePLace")
    public JAXBElement<TogglePLace> createTogglePLace(TogglePLace value) {
        return new JAXBElement<>(_TogglePLace_QNAME, TogglePLace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TogglePLaceResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://soap.parking_system.soa.agh.edu.pl/", name = "togglePLaceResponse")
    public JAXBElement<TogglePLaceResponse> createTogglePLaceResponse(TogglePLaceResponse value) {
        return new JAXBElement<>(_TogglePLaceResponse_QNAME, TogglePLaceResponse.class, null, value);
    }

}
