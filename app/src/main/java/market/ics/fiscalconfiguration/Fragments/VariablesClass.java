package market.ics.fiscalconfiguration.Fragments;

import java.util.List;

import market.ics.fiscalconfiguration.Driver.DataType.ByteBits;
import market.ics.fiscalconfiguration.Driver.Response.GetModemParametrs;
import market.ics.fiscalconfiguration.Driver.Response.ManufacturingDetails;
import market.ics.fiscalconfiguration.Driver.Response.Status.Status;

public class VariablesClass {

    private static Boolean ifThereResponseFromPrinter;


    private static String manufacturer;
    private static String serialNumber;
    private static String manufacturerDate;
    private static String romVersion;
    private static String isFiscal;
    private static String isCutter;
    private static String cashboxResponse;

    private static String modemIp;
    private static String modemMask;
    private static String modemGateway;

    private static String VAT_A;
    private static String VAT_B;
    private static String VAT_C;
    private static String VAT_D;
    private static String VAT_E;

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        VariablesClass.result = result;
    }

    public static String getReserv() {
        return reserv;
    }

    public static void setReserv(String reserv) {
        VariablesClass.reserv = reserv;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        VariablesClass.status = status;
    }

    private static String result;
    private static String reserv;
    private static String status;

    public static double getCashBoxSUmm() {
        return CashBoxSUmm;
    }

    public static void setCashBoxSUmm(double cashBoxSUmm) {
        CashBoxSUmm = cashBoxSUmm;
    }

    private static double CashBoxSUmm;

    private static List<Byte> packet;

    public static List<Byte> getPacket() {
        return packet;
    }

    public static void setPacket(List<Byte> packet) {
        VariablesClass.packet = packet;
    }

    public static String getRomVersion() {
        return romVersion;
    }

    public  static void setRomVersion(String romVersion) {
        VariablesClass.romVersion = romVersion;
    }

    public static String getManufacturer() {
        return manufacturer;
    }

    public  static void setManufacturer(String manufacturer) {

        VariablesClass.manufacturer = manufacturer;
    }

    public static String getSerialNumber() {
        return serialNumber;
    }

    public  static void setSerialNumber(String serialNumber) {
        VariablesClass.serialNumber = serialNumber;
    }

    public static String getManufacturerDate() {
        return manufacturerDate;
    }

    public  static void setManufacturerDate(String manufacturerDate) {
        VariablesClass.manufacturerDate = manufacturerDate;
    }

    public static String getIsfiscal() {
        return isFiscal;
    }

    public  static void setIsfiscal(String isfiscal) {
        VariablesClass.isFiscal = isfiscal;
    }

    public static String getIscutter() {
        return isCutter;
    }

    public static void setIscutter(String iscutter) {
        VariablesClass.isCutter = iscutter;
    }

    public static String getCashboxResponse() {
        return cashboxResponse;
    }

    public  static void setCashboxResponse(String cashboxResponse) {
        VariablesClass.cashboxResponse = cashboxResponse;
    }

    public static String getModemIp() {
        return modemIp;
    }

    public  static void setModemIp(String modemIp) {
        VariablesClass.modemIp = modemIp;
    }

    public static String getModemMask() {
        return modemMask;
    }

    public  static void setModemMask(String modemMask) {
        VariablesClass.modemMask = modemMask;
    }

    public static String getModemGateway() {
        return modemGateway;
    }

    private static void setModemGateway(String modemGateway) {
        VariablesClass.modemGateway = modemGateway;
    }

    public static String getVatA() {
        return VAT_A;
    }

    public static void setVatA(String vatA) {
        VAT_A = vatA;
    }

    public static String getVatB() {
        return VAT_B;
    }

    public static void setVatB(String vatB) {
        VAT_B = vatB;
    }

    public static String getVatC() {
        return VAT_C;
    }

    public static void setVatC(String vatC) {
        VAT_C = vatC;
    }

    public static String getVatD() {
        return VAT_D;
    }

    public static void setVatD(String vatD) {
        VAT_D = vatD;
    }

    public static String getVatE() {
        return VAT_E;
    }

    public static void setVatE(String vatE) {
        VAT_E = vatE;
    }



    /**
     * Что-то будет выполнятся
     */

    public static void Initialize(){

        //ResponseFromPrinter.decode(Commands.sendMessageBoss(0, null));
        //List<Byte> statusData = new CommandResponse(serialResponse.dataBytes(bs.sendMessage(new GetStatusCommand()))).getData();
        //List<Byte> modemSData = new CommandResponse(serialResponse.dataBytes(bs.sendMessage(new GetModemCommand(new PrinterPassword(0))))).getData();


        /**
         * Manufacturing details
         */
        ManufacturingDetails manufacturingDetails = new ManufacturingDetails(getPacket());

        setSerialNumber(manufacturingDetails.serialNumber());
        setManufacturerDate(manufacturingDetails.getManufacturingDate());
        setRomVersion(manufacturingDetails.softwareVersion());
        System.out.println(getRomVersion());
        setManufacturer("IKS-810");



        /**
         * Status Details
         */

        Status status = new Status(new ByteBits(getPacket().get(0)), new ByteBits(getPacket().get(1)));

        setIsfiscal(status.printerConfig().isFiscalized() ? "Выполнена" : "Не выполнена");
        setIscutter(status.printerConfig().isPaperCuttingForbidden() ? "Выключен" : "Включен");
        setCashboxResponse(status.printerConfig().isCashDrawerOpen() ? "Открыт" : "Закрыт");
        System.out.println(getCashboxResponse());
/*
        *//**
         * Modem Details
         */

        GetModemParametrs modemData = new GetModemParametrs(getPacket());

       setModemIp(modemData.getIpAdress());
       setModemMask(modemData.getMask());
       setModemGateway(modemData.getSubnet());


        /**
        * VAT Details
        *//*

        List<Byte> vatData = new CommandResponse(serialResponse.dataBytes(bs.sendMessage(new GetVatCommand()))).getData();
        VatTransform.VatTransform(vatData);*/


    }

    public static void setmodem(){
        GetModemParametrs modemData = new GetModemParametrs(getPacket());

        setModemIp(modemData.getIpAdress());
        setModemMask(modemData.getMask());
        setModemGateway(modemData.getSubnet());
        System.out.println(getModemIp());
        System.out.println(getModemMask());
        System.out.println(getModemMask());
    }

    public static Boolean getIfThereResponseFromPrinter() {
        return ifThereResponseFromPrinter;
    }

    public static void setIfThereResponseFromPrinter(Boolean ifThereResponseFromPrinter) {
        VariablesClass.ifThereResponseFromPrinter = ifThereResponseFromPrinter;
    }
}
