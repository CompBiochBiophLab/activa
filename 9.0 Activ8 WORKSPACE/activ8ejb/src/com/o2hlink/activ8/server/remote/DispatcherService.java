package com.o2hlink.activ8.server.remote;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.o2hlink.activ8.client.action.AllowedUserAddAction;
import com.o2hlink.activ8.client.action.AllowedUserListGetAction;
import com.o2hlink.activ8.client.action.AllowedUserRemoveAction;
import com.o2hlink.activ8.client.action.AssignedDrugListGetAction;
import com.o2hlink.activ8.client.action.AssignedDrugNewAction;
import com.o2hlink.activ8.client.action.AssignedDrugRemoveAction;
import com.o2hlink.activ8.client.action.AssignedMeasurementListGetAction;
import com.o2hlink.activ8.client.action.AssignedMeasurementNewAction;
import com.o2hlink.activ8.client.action.AssignedMeasurementRemoveAction;
import com.o2hlink.activ8.client.action.AssignedProcedureListGetAction;
import com.o2hlink.activ8.client.action.AssignedProcedureNewAction;
import com.o2hlink.activ8.client.action.AssignedProcedureRemoveAction;
import com.o2hlink.activ8.client.action.CommentListGetAction;
import com.o2hlink.activ8.client.action.CommentNewAction;
import com.o2hlink.activ8.client.action.CommentRemoveAction;
import com.o2hlink.activ8.client.action.AccountListGetAction;
import com.o2hlink.activ8.client.action.AccountNewAction;
import com.o2hlink.activ8.client.action.AccountRemoveAction;
import com.o2hlink.activ8.client.action.ContactNewAction;
import com.o2hlink.activ8.client.action.ContactRemoveAction;
import com.o2hlink.activ8.client.action.ContactRequestNewAction;
import com.o2hlink.activ8.client.action.ContactRequestRemoveAction;
import com.o2hlink.activ8.client.action.CountryListGetAction;
import com.o2hlink.activ8.client.action.DatasetListGetAction;
import com.o2hlink.activ8.client.action.DatasetNewAction;
import com.o2hlink.activ8.client.action.DatasetRemoveAction;
import com.o2hlink.activ8.client.action.DiseaseListGetAction;
import com.o2hlink.activ8.client.action.DiseaseNewAction;
import com.o2hlink.activ8.client.action.DrugListGetAction;
import com.o2hlink.activ8.client.action.DrugNewAction;
import com.o2hlink.activ8.client.action.EventListGetAction;
import com.o2hlink.activ8.client.action.EventNewAction;
import com.o2hlink.activ8.client.action.EventRemoveAction;
import com.o2hlink.activ8.client.action.ExonListGetAction;
import com.o2hlink.activ8.client.action.FeedListGetAction;
import com.o2hlink.activ8.client.action.FeedNewAction;
import com.o2hlink.activ8.client.action.FeedRemoveAction;
import com.o2hlink.activ8.client.action.GroupListGetAction;
import com.o2hlink.activ8.client.action.HistoryGetAction;
import com.o2hlink.activ8.client.action.HistoryRecordNewAction;
import com.o2hlink.activ8.client.action.HistoryRecordRemoveAction;
import com.o2hlink.activ8.client.action.InstitutionListGetAction;
import com.o2hlink.activ8.client.action.InstitutionNewAction;
import com.o2hlink.activ8.client.action.MeasurementListGetAction;
import com.o2hlink.activ8.client.action.MeasurementNewAction;
import com.o2hlink.activ8.client.action.MembershipRequestNewAction;
import com.o2hlink.activ8.client.action.MembershipRequestRemoveAction;
import com.o2hlink.activ8.client.action.PaperListGetAction;
import com.o2hlink.activ8.client.action.PaperNewAction;
import com.o2hlink.activ8.client.action.PaperRemoveAction;
import com.o2hlink.activ8.client.action.PatientListGetAction;
import com.o2hlink.activ8.client.action.PatientListNewAction;
import com.o2hlink.activ8.client.action.PatientNewAction;
import com.o2hlink.activ8.client.action.ProcedureListGetAction;
import com.o2hlink.activ8.client.action.ProcedureNewAction;
import com.o2hlink.activ8.client.action.ProjectListGetAction;
import com.o2hlink.activ8.client.action.ProjectNewAction;
import com.o2hlink.activ8.client.action.ProteinRegionListGetAction;
import com.o2hlink.activ8.client.action.ProvinceListGetAction;
import com.o2hlink.activ8.client.action.SearchAction;
import com.o2hlink.activ8.client.action.SubsetListGetAction;
import com.o2hlink.activ8.client.action.SubsetNewAction;
import com.o2hlink.activ8.client.action.SubsetRemoveAction;
import com.o2hlink.activ8.client.action.UserListGetAction;
import com.o2hlink.activ8.client.action.UserLoginAction;
import com.o2hlink.activ8.client.action.WorkpackageListGetAction;
import com.o2hlink.activ8.client.action.WorkpackageNewAction;
import com.o2hlink.activ8.client.exception.ServerException;
import com.o2hlink.activ8.client.response.AssignedMeasurementListResponse;
import com.o2hlink.activ8.client.response.AssignedMeasurementResponse;
import com.o2hlink.activ8.client.response.CommentListResponse;
import com.o2hlink.activ8.client.response.CommentResponse;
import com.o2hlink.activ8.client.response.AccountListResponse;
import com.o2hlink.activ8.client.response.AccountResponse;
import com.o2hlink.activ8.client.response.CountryListResponse;
import com.o2hlink.activ8.client.response.DatasetListResponse;
import com.o2hlink.activ8.client.response.DatasetResponse;
import com.o2hlink.activ8.client.response.DiseaseListResponse;
import com.o2hlink.activ8.client.response.DiseaseResponse;
import com.o2hlink.activ8.client.response.DrugListResponse;
import com.o2hlink.activ8.client.response.DrugResponse;
import com.o2hlink.activ8.client.response.EventListResponse;
import com.o2hlink.activ8.client.response.ExonListResponse;
import com.o2hlink.activ8.client.response.FeedListResponse;
import com.o2hlink.activ8.client.response.FeedResponse;
import com.o2hlink.activ8.client.response.GroupListResponse;
import com.o2hlink.activ8.client.response.HistoryRecordListResponse;
import com.o2hlink.activ8.client.response.HistoryRecordResponse;
import com.o2hlink.activ8.client.response.InstitutionListResponse;
import com.o2hlink.activ8.client.response.InstitutionResponse;
import com.o2hlink.activ8.client.response.MeasurementListResponse;
import com.o2hlink.activ8.client.response.MeasurementResponse;
import com.o2hlink.activ8.client.response.PaperListResponse;
import com.o2hlink.activ8.client.response.PaperResponse;
import com.o2hlink.activ8.client.response.PatientListResponse;
import com.o2hlink.activ8.client.response.PatientResponse;
import com.o2hlink.activ8.client.response.ProcedureListResponse;
import com.o2hlink.activ8.client.response.ProcedureResponse;
import com.o2hlink.activ8.client.response.ProjectListResponse;
import com.o2hlink.activ8.client.response.ProjectResponse;
import com.o2hlink.activ8.client.response.ProteinRegionListResponse;
import com.o2hlink.activ8.client.response.ProvinceListResponse;
import com.o2hlink.activ8.client.response.SearchResponse;
import com.o2hlink.activ8.client.response.SubsetListResponse;
import com.o2hlink.activ8.client.response.UserListResponse;
import com.o2hlink.activ8.client.response.UserResponse;
import com.o2hlink.activ8.client.response.VoidResponse;
import com.o2hlink.activ8.client.response.WorkpackageListResponse;
import com.o2hlink.activ8.client.response.WorkpackageResponse;

/**
 * @author Miguel Angel Hernandez
 **/
@WebService
public interface DispatcherService {
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAllowedUserAdd(@WebParam(name="action") AllowedUserAddAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public UserListResponse dispatchAllowedUserListGet(@WebParam(name="action") AllowedUserListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAllowedUserRemove(@WebParam(name="action") AllowedUserRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DrugListResponse dispatchAssignedDrugListGet(@WebParam(name="action") AssignedDrugListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAssignedDrugNew(@WebParam(name="action") AssignedDrugNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAssignedDrugRemove(@WebParam(name="action") AssignedDrugRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public AssignedMeasurementListResponse dispatchAssignedMeasurementListGet(@WebParam(name="action") AssignedMeasurementListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public AssignedMeasurementResponse dispatchAssignedMeasurementNew(@WebParam(name="action") AssignedMeasurementNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAssignedMeasurementRemove(@WebParam(name="action") AssignedMeasurementRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProcedureListResponse dispatchAssignedProcedureListGet(@WebParam(name="action") AssignedProcedureListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAssignedProcedureNew(@WebParam(name="action") AssignedProcedureNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchAssignedProcedureRemove(@WebParam(name="action") AssignedProcedureRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public CommentListResponse dispatchCommentList(@WebParam(name="action") CommentListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public CommentResponse dispatchCommentNew(@WebParam(name="action") CommentNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchCommentRemove(@WebParam(name="action") CommentRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public AccountListResponse dispatchCommunicationListGet(@WebParam(name="action") AccountListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public AccountResponse dispatchCommunicationNew(@WebParam(name="action") AccountNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchCommunicationRemove(@WebParam(name="action") AccountRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchContactNew(@WebParam(name="action") ContactNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchContactRemove(@WebParam(name="action") ContactRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchContactRequestNew(@WebParam(name="action") ContactRequestNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchContactRequestRemove(@WebParam(name="action") ContactRequestRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public CountryListResponse dispatchCountryListGet(@WebParam(name="action") CountryListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DatasetListResponse dispatchDatasetListGet(@WebParam(name="action") DatasetListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DatasetResponse dispatchDatasetNew(@WebParam(name="action") DatasetNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchDatasetRemove(@WebParam(name="action") DatasetRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DiseaseListResponse dispatchDiseaseListGet(@WebParam(name="action") DiseaseListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DiseaseResponse dispatchDiseaseNew(@WebParam(name="action") DiseaseNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DrugListResponse dispatchDrugListGet(@WebParam(name="action") DrugListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public DrugResponse dispatchDrugNew(@WebParam(name="action") DrugNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public EventListResponse dispatchEventListGet(@WebParam(name="action") EventListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public EventListResponse dispatchEventNew(@WebParam(name="action") EventNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchEventRemove(@WebParam(name="action") EventRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ExonListResponse dispatchExonListGet(@WebParam(name="action") ExonListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public FeedListResponse dispatchFeedListGet(@WebParam(name="action") FeedListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public FeedResponse dispatchFeedNew(@WebParam(name="action") FeedNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchFeedRemove(@WebParam(name="action") FeedRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public GroupListResponse dispatchGroupListGet(@WebParam(name="action") GroupListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public HistoryRecordListResponse dispatchHistoryGet(@WebParam(name="action") HistoryGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public HistoryRecordResponse dispatchHistoryRecordNew(@WebParam(name="action") HistoryRecordNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchHistoryRecordRemove(@WebParam(name="action") HistoryRecordRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public InstitutionListResponse dispatchInstitutionListGet(@WebParam(name="action") InstitutionListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public InstitutionResponse dispatchInstitutionNew(@WebParam(name="action") InstitutionNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public MeasurementListResponse dispatchMeasurementListGet(@WebParam(name="action") MeasurementListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public MeasurementResponse dispatchMeasurementNew(@WebParam(name="action") MeasurementNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchMembershipRequestNew(@WebParam(name="action") MembershipRequestNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchMembershipRequestRemove(@WebParam(name="action") MembershipRequestRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public PaperListResponse dispatchPaperListGet(@WebParam(name="action") PaperListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public PaperResponse dispatchPaperNew(@WebParam(name="action") PaperNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchPaperRemove(@WebParam(name="action") PaperRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public PatientListResponse dispatchPatientListGet(@WebParam(name="action") PatientListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchPatientListNew(@WebParam(name="action") PatientListNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public PatientResponse dispatchPatientNew(@WebParam(name="action") PatientNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProcedureListResponse dispatchProcedureListGet(@WebParam(name="action") ProcedureListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProcedureResponse dispatchProcedureNew(@WebParam(name="action") ProcedureNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProjectListResponse dispatchProjectListGet(@WebParam(name="action") ProjectListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProjectResponse dispatchProjectNew(@WebParam(name="action") ProjectNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProteinRegionListResponse dispatchProteinRegionListGet(@WebParam(name="action") ProteinRegionListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public ProvinceListResponse dispatchProvinceListGet(@WebParam(name="action") ProvinceListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public SearchResponse dispatchSearch(@WebParam(name="action") SearchAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public SubsetListResponse dispatchSubsetListGet(@WebParam(name="action") SubsetListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchSubsetNew(@WebParam(name="action") SubsetNewAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public VoidResponse dispatchSubsetRemove(@WebParam(name="action") SubsetRemoveAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public UserListResponse dispatchUserListGet(@WebParam(name="action") UserListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public UserResponse dispatchUserLogin(@WebParam(name="action") UserLoginAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public WorkpackageListResponse dispatchWorkpackageListGet(@WebParam(name="action") WorkpackageListGetAction action) throws ServerException;
	/**
	 * 
	 **/
	@WebResult(name="response")
	public WorkpackageResponse dispatchWorkpackageNew(@WebParam(name="action") WorkpackageNewAction action) throws ServerException;
}
